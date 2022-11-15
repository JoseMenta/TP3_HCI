package com.example.tp3_hci.utilities

import androidx.navigation.NavController

class NavigationUtilities(
    private val navController: NavController
) {

    fun navigateToRoute(route: String){
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigateToPreviousScreen(){
        navController.navigateUp()
    }

}