package com.example.tp3_hci.utilities.navigation

data class SearchNavigation(
    private val searchScreen : (String, Boolean, Int, Int, String?)->Unit
) {

    fun getSearchScreen() : (String, Boolean, Int, Int, String?)->Unit {
        return searchScreen
    }
}