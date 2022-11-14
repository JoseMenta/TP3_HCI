package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

//AKA FullCycleExercise
data class NetworkCycleExercise(
    @SerializedName("exercise"    ) var exercise    : NetworkExercise? = null,
    @SerializedName("order"       ) var order       : Int,
    @SerializedName("duration"    ) var duration    : Int,
    @SerializedName("repetitions" ) var repetitions : Int,
    @SerializedName("metadata"    ) var metadata    : String?   = null
)
