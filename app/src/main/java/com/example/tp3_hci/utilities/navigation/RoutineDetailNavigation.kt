package com.example.tp3_hci.utilities.navigation

data class RoutineDetailNavigation(
    private val previousScreen : ()->Unit,
    private val executeRoutineScreen: (String)->Unit
) {

    fun getPreviousScreen() : ()->Unit{
        return previousScreen
    }

    fun getExecuteRoutineScreen() : (String)->Unit {
        return executeRoutineScreen
    }
}