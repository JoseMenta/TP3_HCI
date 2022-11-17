package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.ui_state.RatingUiState
import kotlinx.coroutines.launch

class RatingViewModel(
    private val RoutineRepository : RoutineRepository
) : ViewModel() {

    var uiState by mutableStateOf(RatingUiState())
        private set

    fun getRoutineOverview(routineId: Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            RoutineRepository.getRoutineOverview(routineId)
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
    fun ratingRoutine(routineId: Int) = viewModelScope.launch {
        uiState = uiState.copy(
            isFetching = true,
            message = null
        )
        runCatching {
            RoutineRepository.addRoutineReview(routineId, uiState.rating, "")
        }.onSuccess { response ->
            uiState = uiState.copy(
                isFetching = false,
            )
        }.onFailure { e ->
            // Handle the error and notify the UI when appropriate.
            uiState = uiState.copy(
                message = e.message,
                isFetching = false)
        }
    }

    fun changeRating(rating: Int) = viewModelScope.launch {
        uiState = uiState.copy(
            rating = rating
        )
    }
}
