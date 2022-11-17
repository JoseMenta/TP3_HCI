package com.example.tp3_hci.data.network.model

import com.example.api_fiti.data.network.model.NetworkPagedContent
import com.example.tp3_hci.data.network.RemoteDataSource
import com.example.tp3_hci.data.network.api.ApiCategoryService

class CategoryRemoteDataSource(
    private val apiCategoryService: ApiCategoryService
):RemoteDataSource() {
    suspend fun getCategories(page: Int, search: String? = null): NetworkPagedContent<NetworkCategory> {
        return handleApiResponse {
            apiCategoryService.getCategories(page,search)
        }
    }
}