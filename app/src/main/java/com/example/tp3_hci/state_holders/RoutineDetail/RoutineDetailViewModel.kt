package com.example.tp3_hci.state_holders.RoutineDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.data.repository.RoutineRepository
import kotlinx.coroutines.launch

class RoutineDetailViewModel(
    private val routineRepository: RoutineRepository
):ViewModel() {
    var uiState by mutableStateOf(RoutineDetailUiState())
        private set

    fun getRoutineDetails(routineId: Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            routineRepository.getRoutineDetails(routineId)
        }.onSuccess {
            uiState = uiState.copy(
                isFetching = false,
                routine = it
            )
        }.onFailure { e->
            uiState = uiState.copy(
                message = e.message,
                isFetching = false
            )
        }
    }
}