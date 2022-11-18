package com.example.tp3_hci.data.ui_state

import com.example.tp3_hci.data.model.User

data class ProfileUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val message: Int? = null,
) {}
