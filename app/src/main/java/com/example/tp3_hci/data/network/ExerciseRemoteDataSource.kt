package com.example.tp3_hci.data.network

import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.tp3_hci.data.network.RemoteDataSource
import com.example.tp3_hci.data.network.api.ApiExerciseService
import com.example.tp3_hci.data.network.model.NetworkCycleExercise
import com.example.tp3_hci.data.network.model.NetworkExercise
import com.example.tp3_hci.data.network.model.NetworkImage

class ExerciseRemoteDataSource(
    private val apiExerciseService: ApiExerciseService
): RemoteDataSource() {
    suspend fun getCurrentUserExercises(page: Int):NetworkPagedContent<NetworkExercise>{
        return handleApiResponse {
            apiExerciseService.getCurrentUserExercises(page)
        }
    }
    suspend fun getExerciseById(exerciseId: Int):NetworkExercise{
        return handleApiResponse {
            apiExerciseService.getExerciseById(exerciseId)
        }
    }
    suspend fun getExerciseImages(exerciseId: Int, page: Int): NetworkPagedContent<NetworkImage>{
        return handleApiResponse {
            apiExerciseService.getExerciseImages(exerciseId,page)
        }
    }
    suspend fun getExerciseImageById(exerciseId: Int, imageId: Int): NetworkImage{
        return handleApiResponse {
            apiExerciseService.getExerciseImageById(exerciseId, imageId)
        }
    }
}