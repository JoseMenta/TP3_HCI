package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkReviewPost(
    @SerializedName("score"  ) var score  : Int = 0,
    @SerializedName("review" ) var review : String = ""
)
