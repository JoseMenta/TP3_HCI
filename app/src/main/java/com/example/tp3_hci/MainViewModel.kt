package com.example.tp3_hci

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.data.repository.OrderCriteria
import com.example.tp3_hci.data.repository.OrderDirection
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.repository.UserRepository
import com.example.tp3_hci.util.SessionManager
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val routineRepository: RoutineRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
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

    fun logout() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            userRepository.logout()
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                isAuthenticated = false,
                currentUser = null,
                routines = null
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun getCurrentUser() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            userRepository.getCurrentUser(uiState.currentUser == null)
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
                currentUser = response
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun getRoutines() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
//            routineRepository.getCurrentUserRoutineOverviews(true)
            routineRepository.getFilteredRoutineOverviews(
                categoryId = 1,
                difficulty = 1,
                search = "ho",
                orderDirection = OrderDirection.Desc,
            orderCriteria = OrderCriteria.Score)
        }.onSuccess {
            uiState = uiState.copy(
                isFetching = false,
                routines = it
            )
        }.onFailure { e->
            uiState = uiState.copy(
                message = e.message,
                isFetching = false
            )
        }
    }
    fun getDetailedRoutine() = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            routineRepository.getRoutineDetails(14)
        }.onSuccess {
            uiState = uiState.copy(
                isFetching = false,
                detailedRoutine = it
            )
        }.onFailure { e->
            uiState = uiState.copy(
                message = e.message,
                isFetching = false
            )
        }
    }
}