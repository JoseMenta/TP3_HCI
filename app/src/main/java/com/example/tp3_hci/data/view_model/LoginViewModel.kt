package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.MainUiState
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.repository.UserRepository
import com.example.tp3_hci.data.ui_state.LoginUiState
import com.example.tp3_hci.util.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionManager : SessionManager,
    private val userRepository : UserRepository,
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun login(username: String, password: String) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            userRepository.login(username, password)
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                isAuthenticated = true
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }


}


