package com.example.tp3_hci.data.ui_state

import com.example.tp3_hci.data.model.RoutineOverview

data class RatingUiState(
    val isFetching: Boolean = false,
    val message: String? = null,
    val rating: Int = 0,
    val routine: RoutineOverview? = null
){

}