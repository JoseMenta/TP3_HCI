package com.example.tp3_hci.data.network.api

import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.tp3_hci.data.network.model.NetworkCategory
import com.example.tp3_hci.data.network.model.NetworkExercise
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCategoryService {
    @GET("categories")
    suspend fun getCategories(@Query("page")page: Int, @Query("search")search: String?): Response<NetworkPagedContent<NetworkCategory>>
}