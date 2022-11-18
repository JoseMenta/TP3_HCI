package com.example.tp3_hci.state_holders.RoutineDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.R
import com.example.tp3_hci.data.network.DataSourceException
import com.example.tp3_hci.data.model.RoutineDetail
import com.example.tp3_hci.data.repository.RoutineRepository
import kotlinx.coroutines.launch

class RoutineDetailViewModel(
    private val routineRepository: RoutineRepository
):ViewModel() {
    var uiState by mutableStateOf(RoutineDetailUiState())
        private set
    private var isFirst = true
    fun getIsFirst():Boolean{
        if(isFirst){
            isFirst = false
            return true
        }
        return isFirst
    }
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
            uiState = if (e is DataSourceException){
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


    fun toggleRoutineFavorite() = viewModelScope.launch {
        kotlin.runCatching {
            if(uiState.routine!!.isFavourite.value){
                routineRepository.unmarkRoutineAsFavourite(uiState.routine!!.id)
            } else {
                routineRepository.markRoutineAsFavourite(uiState.routine!!.id)
            }
        }.onSuccess {
            uiState.routine!!.isFavourite.value = !uiState.routine!!.isFavourite.value
        }.onFailure { e ->
            uiState = if (e is DataSourceException){
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
}