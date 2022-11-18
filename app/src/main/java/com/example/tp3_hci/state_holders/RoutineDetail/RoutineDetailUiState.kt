package com.example.tp3_hci.state_holders.RoutineDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.tp3_hci.data.model.RoutineDetail

data class RoutineDetailUiState(
    val isFetching: Boolean = false,
    val routine: RoutineDetail? = null,
    val message: Int? = null,
    val isFavourite: MutableState<Boolean> = mutableStateOf(true)
)
