package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkExecution (
    @SerializedName("id"          ) var id          : Int?     = null,
    @SerializedName("date"        ) var date        : Int?     = null,
    @SerializedName("duration"    ) var duration    : Int?     = null,
    @SerializedName("wasModified" ) var wasModified : Boolean? = null,
    @SerializedName("routine"     ) var routine     : NetworkRoutine? =null
)