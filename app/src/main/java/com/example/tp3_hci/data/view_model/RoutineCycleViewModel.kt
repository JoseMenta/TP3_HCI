package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tp3_hci.data.ui_state.ExerciseCardUiState
import com.example.tp3_hci.data.ui_state.RoutineCycleUiState

class RoutineCycleViewModel(
    private val routineCycleData: RoutineCycleUiState
) {

    var routineCycleUiState by mutableStateOf(
        routineCycleData
    )
        private set


    // ------------------------------------------------------------------

    // Devuelve un iterador ajustado a la cantidad de repeticiones
    fun getRoutineCycleIterator(): Iterator<ExerciseCardUiState>{
        return RoutineCycleViewModelIterator(routineCycleUiState)
    }

    // Logica del iterador para la ejecucion de un ciclo
    class RoutineCycleViewModelIterator(
        private val routineCycleData: RoutineCycleUiState,
    ) : Iterator<ExerciseCardUiState>{

        var next = 0
        var cycle = 1

        override fun hasNext(): Boolean {
            // Retornara falso si no hay ejercicios, si se supero la cantidad de repeticiones o si se realizo la ultima repeticion del ultimo ejercicio
            if(routineCycleData.exercises.isEmpty() || cycle > routineCycleData.repetitions ||
                (routineCycleData.exercises.size <= next && cycle == routineCycleData.repetitions)){
                return false
            }
            return true
        }

        override fun next(): ExerciseCardUiState {
            if(!hasNext()){
                throw IllegalStateException("No more elements to return")
            }
            if(next >= routineCycleData.exercises.size){
                next = 0
                cycle++
            } else {
                next++
            }
            return routineCycleData.exercises[next-1]
        }

    }
}