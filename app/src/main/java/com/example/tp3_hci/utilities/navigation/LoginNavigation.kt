package com.example.tp3_hci.utilities.navigation


data class LoginNavigation(
    private val onLoginScreen: ()->Unit
) {

    fun getOnLoginScreen() : ()->Unit{
        return onLoginScreen
    }
}