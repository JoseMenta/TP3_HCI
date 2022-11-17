package com.example.api_fiti.data.network.api

import android.content.Context
import com.example.tp3_hci.BuildConfig
import com.example.tp3_hci.data.network.api.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*

object RetrofitClient {

    @Volatile
    private var instance: Retrofit? = null

    private fun getInstance(context: Context): Retrofit =
        instance?: synchronized(this){
            //si a las corutines 2 les da nulo, tengo que sincronizarlo
            instance?: buildRetrofit(context).also {
                instance = it
            }
        }
    private fun buildRetrofit(context: Context): Retrofit{
        val httpLoggingIterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(httpLoggingIterceptor)
            .build()
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, ApiDateTypeAdapter())
            .create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    fun getApiUserService(context: Context): ApiUserService{
        return getInstance(context).create(ApiUserService::class.java)
    }
    fun getApiRoutineService(context: Context): ApiRoutineService{
        return getInstance(context).create(ApiRoutineService::class.java)
    }
    fun getApiCategoryService(context: Context): ApiCategoryService {
        return getInstance(context).create(ApiCategoryService::class.java)
    }
    fun getApiExerciseService(context: Context): ApiExerciseService{
        return getInstance(context).create(ApiExerciseService::class.java)
    }
    fun getApiSportService(context: Context): ApiSportService{
        return getInstance(context).create(ApiSportService::class.java)
    }
}