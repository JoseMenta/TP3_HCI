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
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.ui_state.SearchResultsParameters
import com.example.tp3_hci.data.ui_state.SearchResultsScreenUiState
import com.example.tp3_hci.util.PreferencesManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchResultsViewModel(
    private val routineRepository: RoutineRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    var searchResultsScreenUiState by mutableStateOf(
        SearchResultsScreenUiState(
            searchParameters = SearchResultsParameters(
                text = "",
                searchingRoutineName = true,
                rating = null,
                difficulty = null,
                category = null
            ),
            results = null,
            orderBy = OrderByItem.Name,
            orderType = OrderTypeItem.Descending,
            isLoading = false,
            message = null,
            isFetched = false
        )
    )
        private set


    private var fetchResults : Job? = null


    fun getSimplify() : Boolean {
        return preferencesManager.getSimplify()
    }

    fun onErrorSearch(){
        searchResultsScreenUiState = searchResultsScreenUiState.copy(
            message = R.string.string_search_too_short
        )
    }

    fun reloadSearchResultsScreenContent(){
        getRoutinesFromSearchResult(searchResultsScreenUiState.searchParameters)
    }

    // Devuelve las rutinas que cumplen con la busqueda solicitada
    fun getRoutinesFromSearchResult(
        searchParameters: SearchResultsParameters
    ){
        fetchResults?.cancel()
        fetchResults = viewModelScope.launch {
            searchResultsScreenUiState = searchResultsScreenUiState.copy(
                searchParameters = searchParameters,
                isLoading = true
            )
            kotlin.runCatching {
                routineRepository.getFilteredRoutineOverviews(
                    category = searchResultsScreenUiState.searchParameters.getCategory(),
                    username = if(searchResultsScreenUiState.searchParameters.isSearchingRoutineName()) null else searchResultsScreenUiState.searchParameters.getText(),
                    score = searchResultsScreenUiState.searchParameters.getRating(),
                    difficulty = searchResultsScreenUiState.searchParameters.getDifficulty(),
                    search = if(searchResultsScreenUiState.searchParameters.isSearchingRoutineName()) searchResultsScreenUiState.searchParameters.getText() else null,
                    orderDirection = searchResultsScreenUiState.orderType.orderDirection,
                    orderCriteria = searchResultsScreenUiState.orderBy.criteria
                )
            }.onSuccess { response ->
                searchResultsScreenUiState = searchResultsScreenUiState.copy(
                    results = response.map { routineOverview -> mutableStateOf(routineOverview) },
                    isLoading = false,
                    isFetched = true
                )
            }.onFailure {
                searchResultsScreenUiState = if(it is DataSourceException){
                    searchResultsScreenUiState.copy(
                        isLoading = false,
                        message = it.stringResourceCode
                    )
                } else {
                    searchResultsScreenUiState.copy(
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
            if(searchResultsScreenUiState.results != null){
                val routines = searchResultsScreenUiState.results!!.filter { routineOverview -> routineOverview.value.id == routine.value.id }
                routines.forEach { routine ->
                    routine.value.isFavourite = !routine.value.isFavourite
                }
            }
        }.onFailure {
            searchResultsScreenUiState = if(it is DataSourceException){
                searchResultsScreenUiState.copy(
                    isLoading = false,
                    message = it.stringResourceCode
                )
            } else {
                searchResultsScreenUiState.copy(
                    isLoading = false,
                    message = R.string.unexpected_error
                )
            }
        }
    }


    // Actualiza el tipo de orden y refresca la lista
    fun setOrderByItem(item : OrderByItem){
        if(item != searchResultsScreenUiState.orderBy){
            searchResultsScreenUiState = searchResultsScreenUiState.copy(
                orderBy = item
            )
            getRoutinesFromSearchResult(searchResultsScreenUiState.searchParameters)
        }
    }


    // Actualiza el tipo de orden (ascendente y descendente) y refresca la lista
    fun setOrderTypeItem(item : OrderTypeItem){
        if(item != searchResultsScreenUiState.orderType){
            searchResultsScreenUiState = searchResultsScreenUiState.copy(
                orderType = item
            )
            getRoutinesFromSearchResult(searchResultsScreenUiState.searchParameters)
        }
    }


    // Elimina el mensaje mostrado en el snackbar
    fun dismissMessage(){
        searchResultsScreenUiState = searchResultsScreenUiState.copy(
            message = null
        )
    }

}