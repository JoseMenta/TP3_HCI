package com.example.tp3_hci.state_holders.RoutineDetail

import com.example.tp3_hci.data.model.CycleExercise
import com.example.tp3_hci.data.model.RoutineDetail

data class ExecuteRoutineUiState (
    val isFetching: Boolean = false,
    val routine: RoutineDetail? = null,
    val selectedExercise: CycleExercise? = null,
    val message: String? = null
)