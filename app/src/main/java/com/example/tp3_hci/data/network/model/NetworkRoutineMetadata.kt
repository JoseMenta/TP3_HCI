package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutineMetadata(
    @SerializedName("votes" ) var votes : Int?              = null,
    @SerializedName("tags"  ) var tags  : ArrayList<String> = arrayListOf()

)