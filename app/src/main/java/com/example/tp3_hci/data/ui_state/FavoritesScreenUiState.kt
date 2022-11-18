package com.example.tp3_hci.data.ui_state

import androidx.compose.runtime.MutableState
import com.example.tp3_hci.data.OrderByItem
import com.example.tp3_hci.data.OrderTypeItem
import com.example.tp3_hci.data.model.RoutineOverview

data class FavoritesScreenUiState(
    val favoriteRoutines : List<MutableState<RoutineOverview>>?,
    val orderBy : OrderByItem,
    val orderType : OrderTypeItem,
    val isLoading : Boolean,
    val message : Int?
){
    fun hasError() : Boolean{
        return message != null
    }
}
