package com.example.tp3_hci.data.network.api

import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.tp3_hci.data.network.model.NetworkCycleExercise
import com.example.tp3_hci.data.network.model.NetworkExercise
import com.example.tp3_hci.data.network.model.NetworkImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiExerciseService {

    @GET("exercises")
    suspend fun getCurrentUserExercises(@Query("page")page: Int): Response<NetworkPagedContent<NetworkExercise>>

    @GET("exercises/{exerciseId}")
    suspend fun getExerciseById(@Path("exerciseId") exerciseId: Int): Response<NetworkExercise>

    @GET("exercises/{exerciseId}/images")
    suspend fun getExerciseImages(@Path("exerciseId")exerciseId: Int, @Query("page")page: Int): Response<NetworkPagedContent<NetworkImage>>

    @GET("exercises/{exerciseId}/images/{imageId}")
    suspend fun getExerciseImageById(@Path("exerciseId")exerciseId: Int, @Path("imageId")imageId: Int): Response<NetworkImage>
}