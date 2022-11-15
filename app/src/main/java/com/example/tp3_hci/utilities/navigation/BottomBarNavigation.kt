package com.example.tp3_hci.utilities.navigation

class BottomBarNavigation(
    private val itemScreen : (String)->Unit
) {

    fun getItemScreen() : (String)->Unit {
        return itemScreen
    }
}