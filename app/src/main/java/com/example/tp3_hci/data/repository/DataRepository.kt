package com.example.tp3_hci.data.repository

import com.example.api_fiti.data.network.model.NetworkPagedContent

abstract class DataRepository {
    suspend fun<T:Any> getAll(execute: suspend (page:Int)-> NetworkPagedContent<T>):List<T>{
        var i = 0
        val result = execute(i)
        i+=1
        var aux = result
        while (!aux.isLastPage){
            aux = execute(i)
            result.content.addAll(aux.content)
            i+=1
        }
        return result.content
    }
}