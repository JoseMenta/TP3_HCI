package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.data.OrderByItem
import com.example.tp3_hci.data.OrderTypeItem
import com.example.tp3_hci.data.model.RoutineOverview
import com.example.tp3_hci.data.network.DataSourceException
import com.example.tp3_hci.data.repository.OrderCriteria
import com.example.tp3_hci.data.repository.OrderDirection
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.repository.UserRepository
import com.example.tp3_hci.data.ui_state.MainScreenUiState
import com.example.tp3_hci.util.PreferencesManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.tp3_hci.R

class MainScreenViewModel(
    private val routineRepository: RoutineRepository,
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private var isFirst = true
    var mainScreenUiState by mutableStateOf(
        MainScreenUiState(
            lastRoutinesExecuted = null,
            createdRoutines = null,
            orderBy = OrderByItem.Name,
            orderType = OrderTypeItem.Descending,
            message = null,
            isLoading = false
        )
    )
        private set


    private var fetchCreatedRoutines : Job? = null

    // Se ejecuta al crear una instancia de la clase
    init {
        reloadMainScreenContent()
    }

    fun getIsFirst():Boolean{
        if(isFirst){
            isFirst = false
            return true
        }
        return false
    }
    fun getSimplify() : Boolean {
        return preferencesManager.getSimplify()
    }

    fun changeSimplify() {
        preferencesManager.changeSimplify()
    }

    fun reloadMainScreenContent(){
        getCreatedRoutines()
        getLastExecutionRoutines()
    }

    // Obtiene las rutinas creadas por el usuario ordenados por las opciones definidas actualmente en el UiState
    fun getCreatedRoutines(){
        fetchCreatedRoutines?.cancel()
        fetchCreatedRoutines = viewModelScope.launch {
            dismissMessage()
            mainScreenUiState = mainScreenUiState.copy(
                isLoading = true
            )
            kotlin.runCatching {
                val user = userRepository.getCurrentUser(true)
                if(user != null){
                    routineRepository.getFilteredRoutineOverviews(
                        userId = user.id,
                        orderCriteria = mainScreenUiState.orderBy.criteria,
                        orderDirection = mainScreenUiState.orderType.orderDirection
                    )
                } else {
                    throw DataSourceException(
                        code = 99,
                        message = "You are not logged in",
                        details = null,
                        stringResourceCode = R.string.unauthorized
                    )
                }
            }.onSuccess { response ->
                mainScreenUiState = mainScreenUiState.copy(
                    createdRoutines = response.map { routine -> mutableStateOf(routine) },
                    isLoading = false
                )
            }.onFailure {
                mainScreenUiState = if(it is DataSourceException) {
                    mainScreenUiState.copy(
                        isLoading = false,
                        message = it.stringResourceCode
                    )
                }else{
                    mainScreenUiState.copy(
                        isLoading = false,
                        message = R.string.unexpected_error
                    )
                }
            }
        }
    }

    // Devuelve las ultimas rutinas ejecutadas por el usuario
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
                lastRoutinesExecuted = response.map { execution -> mutableStateOf(execution.routineOverview)  },
                isLoading = false
            )
        }.onFailure {
            mainScreenUiState = if(it is DataSourceException) {
                mainScreenUiState.copy(
                    isLoading = false,
                    message = it.stringResourceCode
                )
            }else{
                mainScreenUiState.copy(
                    isLoading = false,
                    message = R.string.unexpected_error
                )
            }
        }
    }


    // Actualiza el estado de favorito de una rutina para el usuario
    fun toggleRoutineFavorite(routine : MutableState<RoutineOverview>) = viewModelScope.launch {
        kotlin.runCatching {
            if(routine.value.isFavourite){
                routineRepository.unmarkRoutineAsFavourite(routine.value.id)
            } else {
                routineRepository.markRoutineAsFavourite(routine.value.id)
            }
        }.onSuccess {
            if(mainScreenUiState.createdRoutines != null){
                val routines = mainScreenUiState.createdRoutines!!.filter { createdRoutine -> createdRoutine.value.id == routine.value.id }
                routines.forEach{ routine ->
                    routine.value.isFavourite = !routine.value.isFavourite
                }
            }
            if(mainScreenUiState.lastRoutinesExecuted != null){
                val routineExecutions = mainScreenUiState.lastRoutinesExecuted!!.filter { routineExecution -> routineExecution.value.id == routine.value.id }
                routineExecutions.forEach { routine ->
                    routine.value.isFavourite = !routine.value.isFavourite
                }
            }
        }.onFailure {
            mainScreenUiState = if(it is DataSourceException) {
                mainScreenUiState.copy(
                    isLoading = false,
                    message = it.stringResourceCode
                )
            }else{
                mainScreenUiState.copy(
                    isLoading = false,
                    message = R.string.unexpected_error
                )
            }
        }
    }


    // Actualiza el tipo de orden y refresca la lista
    fun setOrderByItem(item : OrderByItem){
        if(item != mainScreenUiState.orderBy){
            mainScreenUiState = mainScreenUiState.copy(
                orderBy = item
            )
            getCreatedRoutines()
        }
    }


    // Actualiza el tipo de orden (ascendente y descendente) y refresca la lista
    fun setOrderTypeItem(item : OrderTypeItem){
        if(item != mainScreenUiState.orderType){
            mainScreenUiState = mainScreenUiState.copy(
                orderType = item
            )
            getCreatedRoutines()
        }

    }


    // Elimina el mensaje mostrado en el snackbar
    fun dismissMessage(){
        mainScreenUiState = mainScreenUiState.copy(
            message = null
        )
    }
}