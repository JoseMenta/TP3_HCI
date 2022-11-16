package com.example.tp3_hci.data.network.model

import com.example.api_fiti.data.network.model.NetworkUser
import com.example.tp3_hci.data.model.RoutineOverview
import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkRoutine(
    @SerializedName("id"         ) var id         : Int,
    @SerializedName("name"       ) var name       : String,
    @SerializedName("detail"     ) var detail     : String,
    @SerializedName("date"       ) var date       : Date,
    @SerializedName("score"      ) var score      : Int,
    @SerializedName("isPublic"   ) var isPublic   : Boolean,
    @SerializedName("difficulty" ) var difficulty : String,
    @SerializedName("metadata"   ) var metadata   : NetworkRoutineMetadata? = null,
    @SerializedName("category"   ) var category   : NetworkCategory? =null,
    @SerializedName("user"       ) var user       : NetworkPublicUser?     = null
)
