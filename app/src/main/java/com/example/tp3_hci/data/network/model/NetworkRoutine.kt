package com.example.tp3_hci.data.network.model

import com.example.api_fiti.data.network.model.NetworkUser
import com.google.gson.annotations.SerializedName

data class NetworkRoutine(
    @SerializedName("id"         ) var id         : Int?      = null,
    @SerializedName("name"       ) var name       : String,
    @SerializedName("detail"     ) var detail     : String,
    @SerializedName("date"       ) var date       : Int?      = null,
    @SerializedName("score"      ) var score      : Int?      = null,
    @SerializedName("isPublic"   ) var isPublic   : Boolean,
    @SerializedName("difficulty" ) var difficulty : String,
    @SerializedName("metadata"   ) var metadata   : NetworkRoutineMetadata? = null,
    @SerializedName("category"   ) var category   : NetworkCategory? =null,
    @SerializedName("user"       ) var user       : NetworkPublicUser?     = null

)
