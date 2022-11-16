package com.example.tp3_hci.utilities.navigation


data class RoutineCardNavigation(
    private val routineDetailScreen: (String)->Unit
) {

    fun getRoutineDetailScreen() : (String)->Unit{
        return routineDetailScreen
    }
}