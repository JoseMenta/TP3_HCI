package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

data class ExerciseMetadata (
    @SerializedName("equipment") var equipment: String? = null,
    @SerializedName("muscleZone") var muscleZone: String? = null,
    @SerializedName("Intensity") var intensity: String? = null,
    @SerializedName("url") var url: String? = null
)