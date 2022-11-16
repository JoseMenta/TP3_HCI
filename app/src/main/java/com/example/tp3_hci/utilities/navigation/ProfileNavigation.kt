package com.example.tp3_hci.utilities.navigation

data class profileNavigation(
    private val LoginNavigation: ()->Unit,
    private val previousScreen: ()->Unit
) {
    fun getLoginNavigation() : ()->Unit {
        return LoginNavigation
    }
    fun getPreviousScreen() : ()->Unit {
        return previousScreen
    }
}

