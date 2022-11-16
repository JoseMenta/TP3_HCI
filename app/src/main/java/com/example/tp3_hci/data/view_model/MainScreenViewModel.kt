package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.data.OrderByItem
import com.example.tp3_hci.data.OrderTypeItem
import com.example.tp3_hci.data.model.RoutineOverview
import com.example.tp3_hci.data.repository.OrderCriteria
import com.example.tp3_hci.data.repository.OrderDirection
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.ui_state.MainScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val routineRepository: RoutineRepository
) : ViewModel() {

    var mainScreenUiState by mutableStateOf(
        MainScreenUiState(
            lastRoutinesExecuted = null,
            createdRoutines = null,
            orderBy = OrderByItem.Name,
            orderType = OrderTypeItem.Descending,
            message = null,
            isLoading = false,
            isFetched = false
        )
    )
        private set


    private var fetchCreatedRoutines : Job? = null

    // Obtiene las rutinas creadas por el usuario ordenados por las opciones definidas actualmente en el UiState
    fun getCreatedRoutines(){
        fetchCreatedRoutines?.cancel()
        fetchCreatedRoutines = viewModelScope.launch {
            dismissMessage()
            mainScreenUiState = mainScreenUiState.copy(
                isLoading = true
            )
            kotlin.runCatching {
                routineRepository.getCurrentUserRoutineOverviews(
                    orderCriteria = mainScreenUiState.orderBy.criteria,
                    orderDirection = mainScreenUiState.orderType.orderDirection
                )
            }.onSuccess { response ->
                mainScreenUiState = mainScreenUiState.copy(
                    createdRoutines = response,
                    isLoading = false,
                    isFetched = true
                )
            }.onFailure {
                mainScreenUiState = mainScreenUiState.copy(
                    isLoading = false,
                    message = it.message
                )
            }
        }
    }


    fun getLastExecutionRoutines() = viewModelScope.launch {
        dismissMessage()
        mainScreenUiState = mainScreenUiState.copy(
            isLoading = true
        )
        kotlin.runCatching {
            routineRepository.getCurrentUserExecutions(
                orderCriteria = OrderCriteria.CreationDate,
                orderDirection = OrderDirection.Desc
            )
        }.onSuccess { response ->
            mainScreenUiState = mainScreenUiState.copy(
                lastRoutinesExecuted = response.map { execution -> execution.routineOverview  },
                isLoading = false,
                isFetched = true
            )
        }.onFailure {
            mainScreenUiState = mainScreenUiState.copy(
                message = it.message,
                isLoading = false
            )
        }
    }


    fun toggleRoutineFavorite(routine : RoutineOverview) = viewModelScope.launch {
        kotlin.runCatching {
            if(routine.isFavourite){
                routineRepository.unmarkRoutineAsFavourite(routine.id)
            } else {
                routineRepository.markRoutineAsFavourite(routine.id)
            }
        }.onSuccess {
            val routineToChange = mainScreenUiState.createdRoutines!!.first { createdRoutine -> createdRoutine.id == routine.id }
            routineToChange.isFavourite = !routineToChange.isFavourite
            mainScreenUiState = mainScreenUiState.copy(
                createdRoutines = mainScreenUiState.createdRoutines
            )
        }.onFailure {
            mainScreenUiState = mainScreenUiState.copy(
                message = it.message
            )
        }
    }


    // Actualiza el tipo de orden y refresca la lista
    fun setOrderByItem(item : OrderByItem){
        mainScreenUiState = mainScreenUiState.copy(
            orderBy = item
        )
        getCreatedRoutines()
    }


    // Actualiza el tipo de orden (ascendente y descendente) y refresca la lista
    fun setOrderTypeItem(item : OrderTypeItem){
        mainScreenUiState = mainScreenUiState.copy(
            orderType = item
        )
        getCreatedRoutines()
    }


    // Elimina el mensaje mostrado en el snackbar
    fun dismissMessage(){
        mainScreenUiState = mainScreenUiState.copy(
            message = null
        )
    }
}