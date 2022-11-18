package com.example.tp3_hci.data.ui_state

import androidx.compose.runtime.MutableState
import com.example.tp3_hci.data.OrderByItem
import com.example.tp3_hci.data.OrderTypeItem
import com.example.tp3_hci.data.model.RoutineOverview

data class SearchResultsScreenUiState(
    val searchParameters: SearchResultsParameters,
    val results : List<MutableState<RoutineOverview>>?,
    val orderBy : OrderByItem,
    val orderType : OrderTypeItem,
    val isLoading : Boolean,
    val isFetched : Boolean,
    val message : Int?
){
    fun hasError() : Boolean{
        return message != null
    }
}


data class SearchResultsParameters(
    private val text : String,
    private val searchingRoutineName : Boolean,
    private val rating : Int?,
    private val difficulty : Int?,
    private val category : String?
){
    fun getText() : String {
        return text
    }

    fun isSearchingRoutineName() : Boolean {
        return searchingRoutineName
    }

    fun getRating() : Int? {
        return rating
    }

    fun getDifficulty() : Int? {
        return difficulty
    }

    fun getCategory() : String? {
        return category
    }
}