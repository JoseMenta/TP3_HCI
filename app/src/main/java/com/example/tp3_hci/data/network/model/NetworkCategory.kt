package com.example.tp3_hci.data.network.model

import com.example.tp3_hci.data.model.Category
import com.google.gson.annotations.SerializedName

data class NetworkCategory(
    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("name"   ) var name   : String,
    @SerializedName("detail" ) var detail : String? = null
){
    fun toCategory():Category{
        return Category(
            id = id?:1,
            name = name,
            detail = detail
        )
    }
}
