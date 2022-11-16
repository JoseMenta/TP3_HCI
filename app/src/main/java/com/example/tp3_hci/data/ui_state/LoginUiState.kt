package com.example.tp3_hci.data.ui_state


data class LoginUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val message: String? = null
) {

}