package com.example.tp3_hci.utilities.navigation


data class ViewRatingNavigation(
    private val homeScreen: ()->Unit,
    private val previousScreen: ()->Unit
) {

    fun getHomeScreen() : ()->Unit {
        return homeScreen
    }

    fun getPreviousScreen() : ()->Unit {
        return previousScreen
    }
}