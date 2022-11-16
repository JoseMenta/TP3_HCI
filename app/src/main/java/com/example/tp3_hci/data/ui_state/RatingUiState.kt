package com.example.tp3_hci.data.ui_state

data class RatingUiState(
    val isFetching: Boolean = false,
    val message: String? = null,
    val rating: Int = 0
){

}