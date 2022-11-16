package com.example.tp3_hci.utilities.navigation

data class SearchNavigation(
    private val searchScreen : (String)->Unit
) {

    fun getSearchScreen() : (String)->Unit {
        return searchScreen
    }
}