package com.example.tp3_hci.data.ui_state

import com.example.tp3_hci.data.OrderByItem
import com.example.tp3_hci.data.OrderTypeItem
import com.example.tp3_hci.data.model.RoutineOverview

data class MainScreenUiState(
    val lastRoutinesExecuted : List<RoutineOverview>?,
    val createdRoutines : List<RoutineOverview>?,
    val orderBy : OrderByItem,
    val orderType : OrderTypeItem,
    val isLoading : Boolean,
    val isFetched : Boolean,
    val message : String?
){
    fun hasError() : Boolean{
        return message != null
    }
}


