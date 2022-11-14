package com.example.tp3_hci.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.tp3_hci.MainViewModel
import com.example.tp3_hci.data.repository.UserRepository

class ViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
    //tengo que pasarle todos los repositorios de la app
) :AbstractSavedStateViewModelFactory(owner,defaultArgs){
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass){
        when{
            isAssignableFrom(MainViewModel::class.java)->
                MainViewModel()
            //esto hay que hacerlo con todos los view model que tenga
            else ->
                throw IllegalArgumentException("Unknow viewModel class ${modelClass.name}")
        }
    }as T
}