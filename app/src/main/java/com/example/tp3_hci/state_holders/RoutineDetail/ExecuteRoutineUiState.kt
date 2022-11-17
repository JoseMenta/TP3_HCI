package com.example.tp3_hci.state_holders.RoutineDetail


import androidx.compose.runtime.MutableState
import com.example.tp3_hci.data.model.CycleExercise
import com.example.tp3_hci.data.model.RoutineDetail

data class ExecuteRoutineUiState(
    val isFetching: Boolean = false,
    val routine: RoutineDetail? = null,
    val selectedExercise: MutableState<CycleExercise>? = null,
    val message: String? = null,
    val exerciseNumber: MutableState<Int>,
)