package com.example.tp3_hci.data.network.api

import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.tp3_hci.data.network.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiRoutineService {

    @GET("users/current/routines")
    suspend fun getCurrentUserRoutines(@Query("page")page: Int): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("users/current/executions")
    suspend fun getCurrentUserExecutions(@Query("page")page: Int): Response<NetworkPagedContent<NetworkExecution>>

    @POST("favourites/{routineId}")
    suspend fun markRoutineAsFavourite(@Path("routineId") routineId: Int): Response<Unit>

    @DELETE("favourites/{routineId}")
    suspend fun unmarkRoutineAsFavourite(@Path("routineId") routineId: Int): Response<Unit>

    @GET ("favourites")
    suspend fun getCurrentUserFavourites(@Query("page")page: Int): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines")
    suspend fun getAllRoutines(@Query("page")page: Int): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines/{routineId}")
    suspend fun getRoutineById(@Path("routineId")routineId: Int): Response<NetworkRoutine>

    @GET("routines/{routineId}/cycles")
    suspend fun getRoutineCycles(@Path("routineId")routineId: Int, @Query("page")page: Int): Response<NetworkPagedContent<NetworkCycle>>

    @GET("routines/{routineId}/cycles/{cycleId}")
    suspend fun getRoutineCycleById(@Path("routineId")routineId: Int, @Path("cycleId")cycleId: Int): Response<NetworkCycle>

    @GET("executions/{routineId}")
    suspend fun getRoutineExecutions(@Path("routineId")routineId: Int, @Query("page")page: Int): Response<NetworkPagedContent<NetworkExecution>>

    @POST("executions/{routineId}")
    suspend fun addRoutineExecution(@Path("RoutineId") routineId: Int,@Body execution: NetworkExecutionPost): Response<NetworkExecution>

    @GET("reviews/{routineId}")
    suspend fun getRoutineReviews(@Path("routineId") routineId: Int, @Query("page")page: Int): Response<NetworkPagedContent<NetworkReview>>

    @POST("reviews/{routineId}")
    suspend fun addRoutineReview(@Path("routineId") routineId: Int, @Body review: NetworkReviewPost): Response<NetworkReviewPostResponse>

}