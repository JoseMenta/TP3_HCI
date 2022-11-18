package com.example.tp3_hci.data.repository

import androidx.compose.runtime.mutableStateOf
import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.tp3_hci.data.model.*
import com.example.tp3_hci.data.network.RoutineRemoteDataSource
import com.example.tp3_hci.data.network.model.NetworkRoutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import kotlin.collections.HashMap

class RoutineRepository(
    private val remoteDataSource: RoutineRemoteDataSource,
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
): DataRepository() {
    private val routineMutex = Mutex()

    private var routineOverviews: List<RoutineOverview> = emptyList()

    private var favouriteRoutinesOverviews: List<RoutineOverview> = emptyList()

    private val difficultyToInt: HashMap<String,Int> = hashMapOf(Pair("rookie",1),Pair("beginner",2),Pair("intermediate",3),Pair("advanced",4),Pair("expert",5))

    private val difficultyToStrig: HashMap<Int, String> = hashMapOf(Pair(1,"rookie"),Pair(2,"beginner"),Pair(3,"intermediate"),Pair(4,"advanced"),Pair(5,"expert"))

    //todo: cache del detalle de la rutina
    private fun networkRoutineToRoutineOverview(networkRoutine: NetworkRoutine):RoutineOverview{
        return RoutineOverview(
            id = networkRoutine.id,
            name = networkRoutine.name,
            score = networkRoutine.score,
            creationDate = networkRoutine.date?:Date(),
            difficulty = difficultyToInt[networkRoutine.difficulty] ?:1,
            category = networkRoutine.category?.toCategory()?: Category(1,"Full body"),
            tags = networkRoutine.metadata?.tags?: emptyList(),
            imageUrl = networkRoutine.metadata?.image?:"",
            isFavourite = favouriteRoutinesOverviews.any { other -> other.id==networkRoutine.id }
        )
    }
    suspend fun getCurrentUserRoutineOverviews(refresh: Boolean = false, orderCriteria: OrderCriteria = OrderCriteria.Name,orderDirection: OrderDirection = OrderDirection.Asc):List<RoutineOverview>{
        if(refresh || routineOverviews.isEmpty()|| favouriteRoutinesOverviews.isEmpty()){
            getFavouritesOverviews()//tenemos que volver a buscar las favoritas para saber si esta entre ellas si se cambiaron
            val result = getAll {   remoteDataSource.getCurrentUserRoutines(it,orderCriteria.apiName, orderDirection.apiName) }
            routineMutex.withLock {
                routineOverviews = result.map {
                    networkRoutineToRoutineOverview(it)
                }
            }
        }
        return routineMutex.withLock{ this.routineOverviews}
    }
    private suspend fun getFilteredRoutineOverviewsForUser(
        category: String? = null,
        userId: Int? = null,
        score: Int? = null,
        difficulty: Int? = null,
        search: String? = null,
        orderCriteria: OrderCriteria = OrderCriteria.Name,
        orderDirection: OrderDirection = OrderDirection.Asc):List<RoutineOverview>
    {
        var categoryId: Int? = null;
        if(category!=null){
            val result = categoryRepository.getCategories(category)
            if(result.isNotEmpty()){
                categoryId = result[0].id
            }
        }
        getFavouritesOverviews()
        val result = getAll {
            remoteDataSource.getFilteredRoutines(
                page = it,
                categoryId = categoryId,
                userId = userId,
                score = score,
                difficulty = difficultyToStrig[difficulty],
                search = search,
                orderCriteria = orderCriteria.apiName,
                orderDirection = orderDirection.apiName
            )
        }
        return result.map {
            networkRoutineToRoutineOverview(it)
        }
    }
    suspend fun getFilteredRoutineOverviews(
        category: String? = null,
        userId: Int? = null,
        username: String?= null,
        score: Int? = null,
        difficulty: Int? = null,
        search: String? = null,
        orderCriteria: OrderCriteria = OrderCriteria.Name,
        orderDirection: OrderDirection = OrderDirection.Asc):List<RoutineOverview>
    {
        if(username!=null && userId == null) {
            //tengo que hacer el filter para cada usuario que me devuelve
            val users = userRepository.getUsers(username)
            val result = arrayListOf<RoutineOverview>()
            users.forEach{
                result.addAll(getFilteredRoutineOverviewsForUser(
                    category = category,
                    userId = it.id,
                    score = score,
                    difficulty = difficulty,
                    search = search,
                    orderCriteria = orderCriteria,
                    orderDirection = orderDirection
                ))
            }
            return result
        }else {
            val result = getFilteredRoutineOverviewsForUser(
                category = category,
                userId = userId,
                score = score,
                difficulty = difficulty,
                search = search,
                orderCriteria = orderCriteria,
                orderDirection = orderDirection
            )
            return result
        }
    }
    suspend fun getFavouritesOverviews(refresh: Boolean = false): List<RoutineOverview>{
        if(refresh || favouriteRoutinesOverviews.isEmpty()){
            val result = getAll { remoteDataSource.getCurrentUserFavouriteRoutines(it) }
            routineMutex.withLock {
                favouriteRoutinesOverviews = result.map {
                    networkRoutineToRoutineOverview(it)
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
        return networkRoutineToRoutineOverview(result)
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
            cycles = cycles.sortedBy { it.order }.map {
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

    suspend fun getCurrentUserExecutions(orderCriteria: OrderCriteria, orderDirection: OrderDirection):List<Execution>{
        getFavouritesOverviews()//tenemos que volver a buscar las favoritas para saber si esta entre ellas si se cambiaron
        val result = getAll { remoteDataSource.getCurrentUserExecutions(it, orderCriteria.apiName, orderDirection.apiName) }
        return result.map {
            Execution(
                id = it.id,
                date = it.date?: Date(),
                duration = it.duration,
                wasModified = it.wasModified,
                routineOverview = networkRoutineToRoutineOverview(it.routine)
            )
        }
    }

    suspend fun addRoutineExecution(routineId: Int, duration: Int){
        remoteDataSource.addRoutineExecution(routineId,duration)
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