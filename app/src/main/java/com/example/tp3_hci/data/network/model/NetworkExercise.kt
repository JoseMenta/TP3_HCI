package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkExercise(
    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("name"     ) var name     : String,
    @SerializedName("detail"   ) var detail   : String,
    @SerializedName("type"     ) var type     : String,
    @SerializedName("date"     ) var date     : Date?    = null,
    @SerializedName("metadata" ) var metadata : ExerciseMetadata? = null
)
