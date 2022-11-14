package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

//AKA FullCycleExercise
data class NetworkCycleExercise(
    @SerializedName("exercise"    ) var exercise    : NetworkExercise? = null,
    @SerializedName("order"       ) var order       : Int?      = null,
    @SerializedName("duration"    ) var duration    : Int?      = null,
    @SerializedName("repetitions" ) var repetitions : Int?      = null,
    @SerializedName("metadata"    ) var metadata    : String?   = null
)
