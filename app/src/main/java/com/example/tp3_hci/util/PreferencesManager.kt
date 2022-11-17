package com.example.tp3_hci.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

class PreferencesManager (context: Context){
    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getSimplify(): Boolean {
        return preferences.getBoolean(SIMPLIFY, false)
    }

    fun changeSimplify() {
        val editor = preferences.edit()
        editor.putBoolean(SIMPLIFY, !getSimplify())
        editor.apply()
    }

    companion object {
        const val PREFERENCES_NAME = "my_app"
        const val SIMPLIFY = "simplify_mode"
    }

}