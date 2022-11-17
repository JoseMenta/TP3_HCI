package com.example.tp3_hci.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.tp3_hci.MainViewModel
import com.example.tp3_hci.data.repository.RoutineRepository
import com.example.tp3_hci.data.repository.UserRepository
import com.example.tp3_hci.data.view_model.LoginViewModel
import com.example.tp3_hci.data.view_model.ProfileViewModel
import com.example.tp3_hci.state_holders.RoutineDetail.ExecuteRoutineViewModel
import com.example.tp3_hci.data.view_model.RatingViewModel
import com.example.tp3_hci.state_holders.RoutineDetail.RoutineDetailViewModel

class ViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val routineRepository: RoutineRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(sessionManager, userRepository, routineRepository)
            isAssignableFrom(LoginViewModel::class.java)->
                LoginViewModel(sessionManager, userRepository)
            isAssignableFrom(ProfileViewModel::class.java)->
                ProfileViewModel(sessionManager, userRepository)
            isAssignableFrom(RoutineDetailViewModel::class.java) ->
                RoutineDetailViewModel(routineRepository)
            isAssignableFrom(RatingViewModel::class.java) ->
                RatingViewModel(routineRepository)
            isAssignableFrom(ExecuteRoutineViewModel::class.java) ->
                ExecuteRoutineViewModel(routineRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}