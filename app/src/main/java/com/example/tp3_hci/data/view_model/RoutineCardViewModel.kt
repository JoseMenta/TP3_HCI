package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tp3_hci.data.model.RoutineOverview
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.ui_state.RoutineCardUiState

class RoutineCardViewModel(
    private val routineRepository: RoutineRepository,
    private val routineData : RoutineCardUiState
): ViewModel() {

    var routineCardUiState by mutableStateOf(
        routineData
    )
        private set

    // ------------------------------------------------------------------

    // Marca o desmarca la rutina como favorito
    suspend fun toggleFavorite(){
        routineCardUiState = routineCardUiState.copy(
            isLoading = true
        )
        kotlin.runCatching {
            if(routineCardUiState.isFavourite){
                routineRepository.unmarkRoutineAsFavourite(routineCardUiState.id)
            } else {
                routineRepository.markRoutineAsFavourite(routineCardUiState.id)
            }
        }.onSuccess {
            routineCardUiState = routineCardUiState.copy(
                isFavourite = !routineCardUiState.isFavourite,
                isLoading = false
            )
        }.onFailure {
            routineCardUiState = routineCardUiState.copy(
                isLoading = false,
                message = it.message
            )
        }
    }


    // Actualiza el puntaje de la rutina
    suspend fun updateScore(){
        var routineOverview: RoutineOverview? = null
        routineCardUiState = routineCardUiState.copy(
            isLoading = true
        )
        kotlin.runCatching {
            routineOverview = routineRepository.getRoutineOverview(routineCardUiState.id)
        }.onSuccess {
            if(routineOverview != null){
                routineCardUiState = routineCardUiState.copy(
                    score = routineOverview!!.score,
                    isLoading = false
                )
            } else {
                routineCardUiState = routineCardUiState.copy(
                    isLoading = false,
                    message = "Error: Fetching new routine score"
                )
            }
        }.onFailure {
            routineCardUiState = routineCardUiState.copy(
                isLoading = false,
                message = it.message
            )
        }
    }


}