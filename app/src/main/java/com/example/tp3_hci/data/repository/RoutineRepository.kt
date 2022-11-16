package com.example.tp3_hci.data.repository

import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.tp3_hci.data.model.*
import com.example.tp3_hci.data.network.RoutineRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import kotlin.collections.HashMap

class RoutineRepository(
    private val remoteDataSource: RoutineRemoteDataSource,
) {
    private val routineMutex = Mutex()

    private var routineOverviews: List<RoutineOverview> = emptyList()

    private var favouriteRoutinesOverviews: List<RoutineOverview> = emptyList()

    private val difficultyToInt: HashMap<String,Int> = hashMapOf(Pair("rookie",1),Pair("beginner",2),Pair("intermediate",3),Pair("advanced",4),Pair("expert",5))

    private val difficultyToStrig: HashMap<Int, String> = hashMapOf(Pair(1,"rookie"),Pair(2,"beginner"),Pair(3,"intermediate"),Pair(4,"advanced"),Pair(5,"expert"))

    private suspend fun<T:Any> getAll(execute: suspend (page:Int)->NetworkPagedContent<T>):List<T>{
        var i = 0
        val result = execute(i)
        i+=1
        var aux = result
        while (!aux.isLastPage){
            aux = execute(i)
            result.content.addAll(aux.content)
            i+=1
        }
        return result.content
    }
    suspend fun getCurrentUserRoutineOverviews(refresh: Boolean = false, orderCriteria: OrderCriteria = OrderCriteria.Name,orderDirection: OrderDirection = OrderDirection.Asc):List<RoutineOverview>{
        if(refresh || routineOverviews.isEmpty()|| favouriteRoutinesOverviews.isEmpty()){
            getFavouritesOverviews()//tenemos que volver a buscar las favoritas para saber si esta entre ellas si se cambiaron
            val result = getAll {   remoteDataSource.getCurrentUserRoutines(it,orderCriteria.apiName, orderDirection.apiName) }
            routineMutex.withLock {
                routineOverviews = result.map {
                    RoutineOverview(
                        id = it.id,
                        name = it.name,
                        score = it.score,
                        creationDate = it.date?:Date(),
                        difficulty = difficultyToInt.get(it.difficulty)?:1,
                        category = it.category?.toCategory()?: Category(1,"Full body"),
                        tags = it.metadata?.tags?: emptyList(),
                        imageUrl = it.metadata?.image?:"",
                        isFavourite = favouriteRoutinesOverviews.any { other -> other.id==it.id }
                    )
                }
            }
        }
        return routineMutex.withLock{ this.routineOverviews}
    }

    suspend fun getFilteredRoutineOverviews(
        categoryId: Int?,
        userId: Int?,
        score: Int?,
        difficulty: String?,
        search: String?,
        orderCriteria: String?,
        orderDirection: String?){

    }
    suspend fun getFavouritesOverviews(refresh: Boolean = false): List<RoutineOverview>{
        if(refresh || favouriteRoutinesOverviews.isEmpty()){
            val result = getAll { remoteDataSource.getCurrentUserFavouriteRoutines(it) }
            routineMutex.withLock {
                favouriteRoutinesOverviews = result.map {
                    RoutineOverview(
                        id = it.id,
                        name = it.name,
                        score = it.score,
                        creationDate = it.date?:Date(),
                        difficulty = difficultyToInt.get(it.difficulty)?:1,
                        category = it.category?.toCategory()?: Category(1,"Full body"),
                        tags = it.metadata?.tags?: emptyList(),
                        imageUrl = it.metadata?.image?:"",
                        isFavourite = true
                    )
                }
            }
        }
        return routineMutex.withLock { this.favouriteRoutinesOverviews }
    }

    suspend fun markRoutineAsFavourite(routineId: Int):Unit{
        remoteDataSource.markRoutineAsFavourite(routineId = routineId)
        //No puedo cambiar la instancia de RoutineOverview porque es inmutable
        routineMutex.withLock {
            favouriteRoutinesOverviews = emptyList()
        }
    }

    suspend fun unmarkRoutineAsFavourite(routineId: Int):Unit{
        remoteDataSource.unmarkRoutineAsFavourite(routineId = routineId)
        routineMutex.withLock { favouriteRoutinesOverviews = emptyList() }
    }

    suspend fun getRoutineOverview(routineId: Int): RoutineOverview{
        getFavouritesOverviews();//si se cambio, entonces las vuelvo a buscar
        val result = remoteDataSource.getRoutineById(routineId)
        return RoutineOverview(
            id = result.id,
            name = result.name,
            isFavourite = favouriteRoutinesOverviews.any{ it.id == result.id},
            creationDate = result.date?:Date(),
            difficulty = difficultyToInt.get(result.difficulty)?:1,
            category = result.category?.toCategory()?: Category(1,"Full body"),
            score = result.score,
            tags = result.metadata?.tags?: emptyList(),
            imageUrl = result.metadata?.image?:""
        )
    }
    //crea una rutina, con los ciclos y los ejercicios adentro
    suspend fun getRoutineDetails(routineId: Int): RoutineDetail{
        val routine = remoteDataSource.getRoutineById(routineId)
        val cycles = getAll { remoteDataSource.getRoutineCycles(routineId,it) }
        return RoutineDetail(
            id = routine.id,
            name = routine.name,
            difficulty = difficultyToInt.get(routine.difficulty)?:1,
            creator =  routine.user?.username?:"",
            rating = routine.score,
            imageUrl = routine.metadata?.image?:"",
            votes = remoteDataSource.getRoutineReviews(routineId,0).totalCount,
            isFavourite = favouriteRoutinesOverviews.any{ it.id == routineId},
            tags = routine.metadata?.tags?: emptyList(),
            cycles = cycles.map {
                val exercises = getAll {page ->  remoteDataSource.getCycleExercises(it.id,page) }
                Cycle(
                    name = it.name,
                    repetitions = it.repetitions,
                    order = it.order,
                    exercises = exercises.map { ex-> ex.toCycleExercise() }
                )
            }
        )
    }


    suspend fun addRoutineReview(routineId: Int, score: Int, review:String = ""){
        remoteDataSource.addRoutineReview(routineId,score,review)
        routineMutex.withLock{
            this.routineOverviews = emptyList()
            this.favouriteRoutinesOverviews = emptyList()
        }
    }

    suspend fun getCurrentUserExecutions():List<Execution>{
        val result = getAll { remoteDataSource.getCurrentUserExecutions(it) }
        return result.map {
            Execution(
                id = it.id,
                date = it.date?: Date(),
                duration = it.duration,
                wasModified = it.wasModified,
                routineOverview = RoutineOverview(
                    id = it.routine.id,
                    name = it.routine.name,
                    score = it.routine.score,
                    creationDate = it.routine.date?:Date(),
                    difficulty = difficultyToInt[it.routine.difficulty] ?:1,
                    category = it.routine.category?.toCategory()?: Category(1,"Full body"),
                    tags = it.routine.metadata?.tags?: emptyList(),
                    imageUrl = it.routine.metadata?.image?:"",
                    isFavourite = favouriteRoutinesOverviews.any { other-> other.id==it.routine.id }
                )
            )
        }
    }
}

enum class OrderCriteria(
    val apiName: String = "name"
){
    Id("id"),
    Name("name"),
    CreationDate("date"),
    Score("score"),
    Difficulty("difficulty"),
    Category("category"),
}
enum class OrderDirection(
    val apiName: String = "asc"
){
    Asc("asc"),
    Desc("desc")
}