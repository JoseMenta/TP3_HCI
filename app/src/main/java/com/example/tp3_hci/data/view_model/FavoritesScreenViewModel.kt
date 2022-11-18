package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_hci.R
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
                routineRepository.getFilteredRoutineOverviews(
                    orderCriteria = favoritesScreenUiState.orderBy.criteria,
                    orderDirection = favoritesScreenUiState.orderType.orderDirection
                )
            }.onSuccess { response ->
                favoritesScreenUiState = favoritesScreenUiState.copy(
                    favoriteRoutines = response.filter { routineOverview -> routineOverview.isFavourite == true }.map { routineOverview -> mutableStateOf(routineOverview) },
                    isLoading = false
                )
            }.onFailure {
                favoritesScreenUiState = if(it is DataSourceException){
                    favoritesScreenUiState.copy(
                        isLoading = false,
                        message = it.stringResourceCode
                    )
                }else{
                    favoritesScreenUiState.copy(
                        isLoading = false,
                        message = R.string.unexpected_error
                    )
                }
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
            if(favoritesScreenUiState.favoriteRoutines != null){
                val routines = favoritesScreenUiState.favoriteRoutines!!.filter { reoutineOverView -> reoutineOverView.value.id == routine.value.id }
                routines.forEach { routine ->
                    routine.value.isFavourite = !routine.value.isFavourite
                }
            }
        }.onFailure {
            favoritesScreenUiState = if(it is DataSourceException){
                favoritesScreenUiState.copy(
                    isLoading = false,
                    message = it.stringResourceCode
                )
            }else{
                favoritesScreenUiState.copy(
                    isLoading = false,
                    message = R.string.unexpected_error
                )
            }
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
}