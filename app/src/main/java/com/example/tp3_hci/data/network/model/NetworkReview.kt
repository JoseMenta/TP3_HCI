package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkReview (
    @SerializedName("id"      ) var id      : Int?     = null,
    @SerializedName("date"    ) var date    : Int?     = null,
    @SerializedName("score"   ) var score   : Int?     = null,
    @SerializedName("review"  ) var review  : String?  = null,
    @SerializedName("routine" ) var routine : NetworkRoutine? = null
)
