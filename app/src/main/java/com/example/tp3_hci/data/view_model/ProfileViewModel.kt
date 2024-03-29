package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.R
import com.example.tp3_hci.data.network.DataSourceException
import com.example.tp3_hci.data.repository.UserRepository
import com.example.tp3_hci.data.ui_state.ProfileUiState
import com.example.tp3_hci.util.PreferencesManager
import com.example.tp3_hci.util.SessionManager
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val preferencesManager : PreferencesManager
) : ViewModel() {

    var uiState by mutableStateOf(ProfileUiState(isAuthenticated = sessionManager.loadAuthToken() != null, simplify = preferencesManager.getSimplify()))
        private set

    init {
        getCurrentUser()
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
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = if(e is  DataSourceException){
                uiState.copy(
                    message = e.stringResourceCode,
                    isFetching = false
                )
            }else{
                uiState.copy(
                    message = R.string.unexpected_error,
                    isFetching = false
                )
            }
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
            uiState = if(e is  DataSourceException){
                uiState.copy(
                    message = e.stringResourceCode,
                    isFetching = false
                )
            }else{
                uiState.copy(
                    message = R.string.unexpected_error,
                    isFetching = false
                )
            }
        }
    }
    fun dismissMessage(){
        uiState = uiState.copy(
            message = null
        )
    }

    fun changeSimplify() {
        preferencesManager.changeSimplify()
        uiState = uiState.copy(
            simplify = preferencesManager.getSimplify()
        )
    }

}