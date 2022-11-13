package com.example.api_fiti.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkSport(
    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("detail" ) var detail : String? = null
)
