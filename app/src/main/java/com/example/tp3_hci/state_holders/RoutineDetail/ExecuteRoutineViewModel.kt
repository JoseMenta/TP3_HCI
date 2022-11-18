package com.example.tp3_hci.state_holders.RoutineDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.R
import com.example.tp3_hci.data.model.CycleExercise
import com.example.tp3_hci.data.model.RoutineDetail
import com.example.tp3_hci.data.network.DataSourceException
import com.example.tp3_hci.data.repository.RoutineRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Date

class ExecuteRoutineViewModel(
    private val routineRepository: RoutineRepository
): ViewModel() {
    var uiState by mutableStateOf(ExecuteRoutineUiState(isFetching = false, message = null, exerciseNumber = mutableStateOf(0)))
        private set
    private var fetchJob: Job? = null
    private var cycleIndex: Int = 0
    private var cycleReps: Int = 0
    private var exerciseIndex: Int = 0
    private val startTime: Date = Date()
    private var isFirst = true

    fun getIsFirst():Boolean{
        if(isFirst){
            isFirst = false
            return true
        }
        return isFirst
    }
    fun getRoutine(routineId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            uiState = uiState.copy(
                isFetching = true,
                message = null
            )
            runCatching {
                routineRepository.getRoutineDetails(routineId)
            }.onSuccess {
                uiState = uiState.copy(
                    isFetching = false,
                    routine = it,
                    selectedExercise = getFirstExercise(it)
                )
                it.cycles[0].exercises[0].isSelected.value = true
            }.onFailure { e ->
                if(e is DataSourceException){
                    uiState = uiState.copy(
                        message = e.stringResourceCode,
                        isFetching = false
                    )
                }else{
                    uiState = uiState.copy(
                        message = R.string.unexpected_error,
                        isFetching = false
                    )
                }
            }
        }
    }
    fun getFirstExercise(routine: RoutineDetail): MutableState<CycleExercise>?{
            while (cycleIndex<routine.cycles.size){
                val cycle = routine.cycles[cycleIndex]
                if(exerciseIndex<cycle.exercises.size){
                    cycleReps = cycle.repetitions
                    return mutableStateOf(routine.cycles[cycleIndex].exercises[exerciseIndex])
                }
                exerciseIndex = 0
            }
        return null
    }
    fun nextExercise(){
        if (uiState.routine != null) {
            uiState.routine!!.cycles[cycleIndex].exercises[exerciseIndex].isSelected.value = false
            if(exerciseIndex == uiState.routine!!.cycles[cycleIndex].exercises.size-1){
                cycleReps-=1
                if(cycleReps==0){
                    cycleIndex+=1
                    if(cycleIndex == uiState.routine!!.cycles.size){
                        cycleIndex-=1
                        //guardar la ejecucion
                        //hacer que llame a hasNext luego para ir a la otra ventana si no
                        return
                    }else{
                        cycleReps = uiState.routine!!.cycles[cycleIndex].repetitions
                        exerciseIndex = 0
                    }
                }else{
                    exerciseIndex = 0
                }
            }else{
                exerciseIndex+=1
            }
            uiState.routine!!.cycles[cycleIndex].exercises[exerciseIndex].isSelected.value = true
            uiState.selectedExercise!!.value = uiState.routine!!.cycles[cycleIndex].exercises[exerciseIndex]
            uiState.exerciseNumber.value += 1
            uiState = uiState.copy(
                hasPrevExercise = hasPrevExercise()
            )
        }
    }
    fun prevExercise(){
        if(uiState.routine!=null){
            uiState.routine!!.cycles[cycleIndex].exercises[exerciseIndex].isSelected.value = false
            if(exerciseIndex==0){
                if(cycleReps == uiState.routine!!.cycles[cycleIndex].repetitions){
                    if(cycleIndex==0){
                        return
                    }
                    cycleIndex-=1
                    exerciseIndex = uiState.routine!!.cycles[cycleIndex].exercises.size-1
                    cycleReps=1
                }else{
                    cycleReps+=1
                    exerciseIndex = uiState.routine!!.cycles[cycleIndex].exercises.size-1
                }
            }else{
                exerciseIndex-=1
            }
            //restart timer
            uiState.routine!!.cycles[cycleIndex].exercises[exerciseIndex].isSelected.value = true
            uiState.selectedExercise!!.value = uiState.routine!!.cycles[cycleIndex].exercises[exerciseIndex]
            uiState.exerciseNumber.value += 1
            uiState = uiState.copy(
                hasPrevExercise = hasPrevExercise()
            )
        }
    }
    fun hasNextExercise():Boolean{
        if(exerciseIndex == uiState.routine!!.cycles[cycleIndex].exercises.size-1
            && cycleReps==1 && cycleIndex == uiState.routine!!.cycles.size-1){
            return false
        }
        return true
    }
    fun hasPrevExercise():Boolean{
        return !(exerciseIndex==0 && cycleIndex==0 && cycleReps == uiState.routine!!.cycles[cycleIndex].repetitions)
    }
    fun addRoutineExecution(routineId: Int){
        val currTime = Date()
        val diff: Int = ((currTime.time - startTime.time)/1000).toInt()
        viewModelScope.launch {  routineRepository.addRoutineExecution(routineId = routineId, duration = diff) }
    }
    fun dismissMessage(){
        uiState = uiState.copy(
            message = null
        )
    }
}