package com.example.tp3_hci.util

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import com.example.tp3_hci.MyApplication

@Composable
fun getViewModelFactory (defaultArgs: Bundle? = null) : ViewModelFactory{
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    val routineRepository = application.routineRepository
    return ViewModelFactory(sessionManager,userRepository,routineRepository,LocalSavedStateRegistryOwner.current,defaultArgs)
}