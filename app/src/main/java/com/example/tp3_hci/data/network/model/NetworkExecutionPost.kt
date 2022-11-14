package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkExecutionPost(
    @SerializedName("duration"    ) var duration    : Int?     = null,
    @SerializedName("wasModified" ) var wasModified : Boolean? = null
)
