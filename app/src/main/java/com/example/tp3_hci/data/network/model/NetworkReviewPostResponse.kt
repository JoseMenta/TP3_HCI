package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkReviewPostResponse(
    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("date"   ) var date   : Date?    = null,
    @SerializedName("score"  ) var score  : Int?    = null,
    @SerializedName("review" ) var review : String? = null

)
