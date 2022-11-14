package com.example.tp3_hci.data.network

import com.example.api_fiti.data.network.api.ApiUserService
import com.example.api_fiti.data.network.model.NetworkCredentials
import com.example.api_fiti.data.network.model.NetworkUser
import com.example.tp3_hci.util.SessionManager

class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val apiUserService: ApiUserService
) : RemoteDataSource(){
    suspend fun login(username: String, password: String){
        val response = handleApiResponse {
            apiUserService.login(NetworkCredentials(username,password))
        }
        sessionManager.saveAuthToken(response.token!!)
    }
    suspend fun logout(){
        handleApiResponse {
            apiUserService.logout()
        }
        sessionManager.removeAuthToken()
    }
    suspend fun getCurrentUser():NetworkUser{
        return handleApiResponse { apiUserService.getCurrentUser() }
    }
}