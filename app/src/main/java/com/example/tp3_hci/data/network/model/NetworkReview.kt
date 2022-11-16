package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkReview (
    @SerializedName("id"      ) var id      : Int?     = null,
    @SerializedName("date"    ) var date    : Date?     = null,
    @SerializedName("score"   ) var score   : Int,
    @SerializedName("review"  ) var review  : String,
    @SerializedName("routine" ) var routine : NetworkRoutine? = null
)
