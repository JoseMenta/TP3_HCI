package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.data.OrderByItem
import com.example.tp3_hci.data.OrderTypeItem
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.ui_state.FavoritesScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritesScreenViewModel(
    private val routineRepository: RoutineRepository
) : ViewModel() {

    var favoritesScreenUiState by mutableStateOf(
        FavoritesScreenUiState(
            favoriteRoutines = null,
            orderBy = OrderByItem.Name,
            orderType = OrderTypeItem.Descending,
            isLoading = false,
            isFetched = false,
            message = null
        )
    )
        private set


    private var fetchFavoriteRoutines : Job? = null

    // Actualiza la lista de rutinas favoritas del usuario
    fun getFavoriteRoutines(){
        fetchFavoriteRoutines?.cancel()
        fetchFavoriteRoutines = viewModelScope.launch {
            dismissMessage()
            favoritesScreenUiState = favoritesScreenUiState.copy(
                isLoading = true
            )
            kotlin.runCatching {
                routineRepository.getFavouritesOverviews(
                    orderCriteria = favoritesScreenUiState.orderBy.criteria,
                    orderDirection = favoritesScreenUiState.orderType.orderDirection
                )
            }.onSuccess { response ->
                favoritesScreenUiState = favoritesScreenUiState.copy(
                    favoriteRoutines = response,
                    isLoading = false,
                    isFetched = true
                )
            }.onFailure {
                favoritesScreenUiState = favoritesScreenUiState.copy(
                    isLoading = false,
                    message = it.message
                )
            }
        }
    }


    // TODO: Marcar y desmarcar favoritos


    // Actualiza el tipo de orden y refresca la lista
    fun setOrderByItem(item : OrderByItem){
        favoritesScreenUiState = favoritesScreenUiState.copy(
            orderBy = item
        )
        getFavoriteRoutines()
    }


    // Actualiza el tipo de orden (ascendente y descendente) y refresca la lista
    fun setOrderTypeItem(item : OrderTypeItem){
        favoritesScreenUiState = favoritesScreenUiState.copy(
            orderType = item
        )
        getFavoriteRoutines()
    }



    // Elimina el mensaje mostrado en el snackbar
    fun dismissMessage(){
        favoritesScreenUiState = favoritesScreenUiState.copy(
            message = null
        )
    }
}