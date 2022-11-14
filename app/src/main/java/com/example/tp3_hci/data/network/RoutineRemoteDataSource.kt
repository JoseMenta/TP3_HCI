package com.example.tp3_hci.data.network

import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.tp3_hci.data.network.api.ApiRoutineService
import com.example.tp3_hci.data.network.model.*

class RoutineRemoteDataSource(
    private val apiRoutineService: ApiRoutineService
): RemoteDataSource() {
    suspend fun getCurrentUserRoutines(page: Int): NetworkPagedContent<NetworkRoutine>{
        return handleApiResponse {
            apiRoutineService.getCurrentUserRoutines(page)
        }
    }
    suspend fun getCurrentUserExecutions(page: Int): NetworkPagedContent<NetworkExecution>{
        return handleApiResponse {
            apiRoutineService.getCurrentUserExecutions(page)
        }
    }
    suspend fun markRoutineAsFavourite(routineId: Int): Unit{
        return handleApiResponse {
            apiRoutineService.markRoutineAsFavourite(routineId)
        }
    }
    suspend fun unmarkRoutineAsFavourite(routineId: Int): Unit{
        return handleApiResponse {
            apiRoutineService.unmarkRoutineAsFavourite(routineId)
        }
    }
    suspend fun getCurrentUserFavouriteRoutines(page: Int):NetworkPagedContent<NetworkRoutine>{
        return handleApiResponse {
            apiRoutineService.getCurrentUserFavouriteRoutines(page)
        }
    }
    suspend fun getAllRoutines(page: Int): NetworkPagedContent<NetworkRoutine>{
        return handleApiResponse {
            apiRoutineService.getAllRoutines(page)
        }
    }
    suspend fun getRoutineById(routineId: Int): NetworkRoutine{
        return handleApiResponse {
            apiRoutineService.getRoutineById(routineId)
        }
    }
    suspend fun getRoutineCycles(routineId: Int, page: Int): NetworkPagedContent<NetworkCycle>{
        return handleApiResponse {
            apiRoutineService.getRoutineCycles(routineId,page)
        }
    }
    suspend fun getRoutineCycleById(routineId: Int, cycleId: Int):NetworkCycle{
        return handleApiResponse {
            apiRoutineService.getRoutineCycleById(routineId,cycleId)
        }
    }
    suspend fun getRoutineExecutions(routineId: Int, page: Int): NetworkPagedContent<NetworkExecution>{
        return handleApiResponse {
            apiRoutineService.getRoutineExecutions(routineId,page)
        }
    }
    suspend fun addRoutineExecution(routineId: Int, duration: Int, wasModified: Boolean = false):NetworkExecution{
        return handleApiResponse {
            apiRoutineService.addRoutineExecution(routineId, NetworkExecutionPost(duration,wasModified))
        }
    }
    suspend fun getRoutineReviews(routineId: Int, page: Int): NetworkPagedContent<NetworkReview>{
        return handleApiResponse {
            apiRoutineService.getRoutineReviews(routineId,page)
        }
    }
    suspend fun addRoutineReview(routineId: Int, score: Int, review: String = ""):NetworkReviewPostResponse{
        return handleApiResponse {
            apiRoutineService.addRoutineReview(routineId,NetworkReviewPost(score,review))
        }
    }
}