package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.example.tp3_hci.data.ui_state.FavoritesScreenUiState
import com.example.tp3_hci.util.PreferencesManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritesScreenViewModel(
    private val routineRepository: RoutineRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    var favoritesScreenUiState by mutableStateOf(
        FavoritesScreenUiState(
            routines = null,
            favoriteRoutines = null,
            orderBy = OrderByItem.Name,
            orderType = OrderTypeItem.Descending,
            isLoading = false,
            message = null
        )
    )
        private set

    private var fetchFavoriteRoutines : Job? = null

    init {
        getFavoriteRoutines()
    }

    fun getSimplify() : Boolean {
        return preferencesManager.getSimplify()
    }

    fun changeSimplify() {
         preferencesManager.changeSimplify()
    }


    // Actualiza el contenido de la pantalla de favoritos
    fun reloadFavoritesScreenContent(){
        getFavoriteRoutines()
    }

    // Actualiza la lista de rutinas favoritas del usuario
    fun getFavoriteRoutines(){
        fetchFavoriteRoutines?.cancel()
        fetchFavoriteRoutines = viewModelScope.launch {
            dismissMessage()
            favoritesScreenUiState = favoritesScreenUiState.copy(
                isLoading = true
            )
            kotlin.runCatching {
                getOrderedRoutines(
                    orderCriteria = favoritesScreenUiState.orderBy.criteria,
                    orderDirection = favoritesScreenUiState.orderType.orderDirection
                )
            }.onSuccess {
                kotlin.runCatching {
                    routineRepository.getFavouritesOverviews()
                }.onSuccess { response ->
                    val favoriteRoutines = favoritesScreenUiState.routines?.intersect(response.toSet())
                    if (favoriteRoutines != null) {
                        favoritesScreenUiState = favoritesScreenUiState.copy(
                            favoriteRoutines = favoriteRoutines.map { routineOverview -> mutableStateOf(routineOverview) },
                            isLoading = false
                        )
                    } else {
                        favoritesScreenUiState = favoritesScreenUiState.copy(
                            favoriteRoutines = null,
                            isLoading = false
                        )
                    }
                }.onFailure {
                    throw it
                }
            }.onFailure {
                favoritesScreenUiState = favoritesScreenUiState.copy(
                    isLoading = false,
                    message = it.message
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

        }.onFailure {
            favoritesScreenUiState = favoritesScreenUiState.copy(
                message = it.message
            )
        }
    }


    // Actualiza el tipo de orden y refresca la lista
    fun setOrderByItem(item : OrderByItem){
        if(item != favoritesScreenUiState.orderBy){
            favoritesScreenUiState = favoritesScreenUiState.copy(
                orderBy = item
            )
            getFavoriteRoutines()
        }
    }


    // Actualiza el tipo de orden (ascendente y descendente) y refresca la lista
    fun setOrderTypeItem(item : OrderTypeItem){
        if(item != favoritesScreenUiState.orderType){
            favoritesScreenUiState = favoritesScreenUiState.copy(
                orderType = item
            )
            getFavoriteRoutines()
        }
    }


    // Elimina el mensaje mostrado en el snackbar
    fun dismissMessage(){
        favoritesScreenUiState = favoritesScreenUiState.copy(
            message = null
        )
    }

    // Obtiene todas las rutinas, las cuales luego se filtraran para quedarse con las favoritas
    private suspend fun getOrderedRoutines(
        orderCriteria: OrderCriteria,
        orderDirection: OrderDirection
    ) {
        viewModelScope.launch {
            favoritesScreenUiState = favoritesScreenUiState.copy(
                routines = null
            )
            kotlin.runCatching {
                routineRepository.getFilteredRoutineOverviews(
                    orderCriteria = orderCriteria,
                    orderDirection = orderDirection
                )
            }.onSuccess { response ->
                favoritesScreenUiState = favoritesScreenUiState.copy(
                    routines = response
                )
            }.onFailure {
                throw it
            }
        }
    }
}