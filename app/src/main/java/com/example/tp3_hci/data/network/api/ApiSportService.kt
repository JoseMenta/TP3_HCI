package com.example.api_fiti.data.network.api

import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.api_fiti.data.network.model.NetworkSport
import retrofit2.Response
import retrofit2.http.*

interface ApiSportService {
    @GET("sports")
    suspend fun getSports(): Response<NetworkPagedContent<NetworkSport>>

    @POST("sports")
    suspend fun addSport(@Body sport: NetworkSport): Response<NetworkSport>

    @GET("sport/{sportId}")
    suspend fun getSport(@Path("sportId") sportId: Int): Response<NetworkSport>

    @PUT("sport/{sportId}")
    suspend fun modifySport(@Path("sportId") sportId: Int, @Body sport: NetworkSport): Response<NetworkSport>

    @DELETE("sport/{sportId}")
    suspend fun deleteSport(@Path("sportId") sportId: Int): Response<Unit>
}