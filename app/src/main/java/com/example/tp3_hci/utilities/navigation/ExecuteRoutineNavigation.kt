package com.example.tp3_hci.utilities.navigation

// TODO: Manejo a la pantalla de ViewRating
data class ExecuteRoutineNavigation(
    private val previousScreen : () -> Unit,
    private val rateRoutineScreen : (String)->Unit
) {

    fun getPreviousScreen() : ()->Unit {
        return previousScreen
    }

    fun getRateRoutineScreen() : (String) -> Unit {
        return rateRoutineScreen
    }
}