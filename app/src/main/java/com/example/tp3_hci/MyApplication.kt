package com.example.tp3_hci

import android.app.Application
import com.example.api_fiti.data.network.api.RetrofitClient
import com.example.tp3_hci.data.network.UserRemoteDataSource
import com.example.tp3_hci.data.repository.UserRepository
import com.example.tp3_hci.util.SessionManager

class MyApplication: Application() {
    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager,RetrofitClient.getApiUserService(this))
    val sessionManager: SessionManager
        get() = SessionManager(this)
    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)
}