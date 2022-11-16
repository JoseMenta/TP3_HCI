package com.example.tp3_hci.state_holders.RoutineDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.data.model.CycleExercise
import com.example.tp3_hci.data.repository.RoutineRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExecuteRoutineViewModel(
    private val routineRepository: RoutineRepository
): ViewModel() {
    var uiState by mutableStateOf(ExecuteRoutineUiState(isFetching = false, message = null))
        private set
    private var fetchJob: Job? = null
    private var cycleIndex: Int = 0
    private var exerciseIndex: Int = 0
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
                    selectedExercise = it.cycles[0].exercises[0]
                )
                it.cycles[0].exercises[0].isSelected = true
            }.onFailure { e ->
                uiState = uiState.copy(
                    message = e.message,
                    isFetching = false
                )
            }
        }
    }
    fun getFirstExercise(): CycleExercise?{
        val routine = uiState.routine
        if(routine!=null){
            while (cycleIndex<routine.cycles.size){
                val cycle = routine.cycles[cycleIndex]
                if(exerciseIndex<cycle.exercises.size){
                    return routine.cycles[cycleIndex].exercises[exerciseIndex]
                }
                exerciseIndex = 0
            }
        }
        return null
    }
    fun nextExercise(){
        if (uiState.routine != null) {
            uiState.routine!!.cycles[0].exercises[0].isSelected = false
            uiState = uiState.copy(
                selectedExercise = uiState.routine!!.cycles[0].exercises[1]
            )
        }
        exerciseIndex += 1
    }
    fun prevExercise(){
        uiState.selectedExercise?.isSelected = false
        exerciseIndex-=1
    }
    fun hasNextExercise():Boolean{
        val routine = uiState.routine
        if(routine!=null) {
            while (cycleIndex<routine.cycles.size){
                val cycle = routine.cycles[cycleIndex]
                if(exerciseIndex<cycle.exercises.size){
                    return true
                }
                exerciseIndex = 0
            }
            return false
        }
        return false
    }
}