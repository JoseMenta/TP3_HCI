package com.example.tp3_hci.utilities.navigation

data class MainScreenNavigation(
    private val routineCardNavigation: RoutineCardNavigation,
    private val searchNavigation: SearchNavigation,
) {

    fun getRoutineCardNavigation() : RoutineCardNavigation {
        return routineCardNavigation
    }

    fun getSearchNavigation() : SearchNavigation {
        return searchNavigation
    }

}