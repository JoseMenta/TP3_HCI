package com.example.api_fiti.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkCredentials (

    @SerializedName("username" ) var username : String? = null,
    @SerializedName("password" ) var password : String? = null

)