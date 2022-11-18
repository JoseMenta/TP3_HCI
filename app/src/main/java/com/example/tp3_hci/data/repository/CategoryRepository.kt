package com.example.tp3_hci.data.repository


import com.example.tp3_hci.data.model.Category

import com.example.tp3_hci.data.network.model.CategoryRemoteDataSource

class CategoryRepository(
    private val remoteDataSource: CategoryRemoteDataSource
): DataRepository(){
    suspend fun getCategories(search: String? = null):List<Category>{
        val result = getAll { remoteDataSource.getCategories(it,search) }
        return result.map { it.toCategory() }
    }
}