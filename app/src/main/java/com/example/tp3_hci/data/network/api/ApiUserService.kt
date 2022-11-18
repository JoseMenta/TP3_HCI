package com.example.api_fiti.data.network.api

import com.example.api_fiti.data.network.model.NetworkCredentials
import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.api_fiti.data.network.model.NetworkToken
import com.example.api_fiti.data.network.model.NetworkUser
import com.example.tp3_hci.data.network.model.NetworkExecution
import com.example.tp3_hci.data.network.model.NetworkPublicUser
import com.example.tp3_hci.data.network.model.NetworkRoutine
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiUserService {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int, @Query("search")search: String? = null):Response<NetworkPagedContent<NetworkPublicUser>>

    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>
}
