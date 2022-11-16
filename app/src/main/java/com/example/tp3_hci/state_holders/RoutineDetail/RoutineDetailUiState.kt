package com.example.tp3_hci.state_holders.RoutineDetail

import com.example.tp3_hci.data.model.RoutineDetail

data class RoutineDetailUiState(
    val isFetching: Boolean = false,
    val routine: RoutineDetail? = null,
    val message: String? = null,
)
