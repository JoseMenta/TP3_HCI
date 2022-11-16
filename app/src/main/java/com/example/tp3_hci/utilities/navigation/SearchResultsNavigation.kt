package com.example.tp3_hci.utilities.navigation

data class SearchResultsNavigation(
    private val previousScreen : ()->Unit,
    private val searchNavigation: SearchNavigation,
    private val routineCardNavigation: RoutineCardNavigation
) {

    fun getPreviousScreen() : ()->Unit {
        return previousScreen
    }

    fun getSearchNavigation() : SearchNavigation {
        return searchNavigation
    }

    fun getRoutineCardNavigation() : RoutineCardNavigation {
        return routineCardNavigation
    }

}