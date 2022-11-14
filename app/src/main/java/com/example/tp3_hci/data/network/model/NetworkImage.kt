package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkImage(
    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("number" ) var number : Int,
    @SerializedName("url"    ) var url    : String
)
