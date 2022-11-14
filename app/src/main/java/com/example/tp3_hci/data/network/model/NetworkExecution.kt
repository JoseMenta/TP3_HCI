package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkExecution (
    @SerializedName("id"          ) var id          : Int,
    @SerializedName("date"        ) var date        : Date?     = null,
    @SerializedName("duration"    ) var duration    : Int,
    @SerializedName("wasModified" ) var wasModified : Boolean = false,
    @SerializedName("routine"     ) var routine     : NetworkRoutine
)