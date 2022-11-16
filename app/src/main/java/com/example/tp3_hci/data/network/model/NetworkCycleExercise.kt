package com.example.tp3_hci.data.network.model

import com.example.tp3_hci.data.model.CycleExercise
import com.google.gson.annotations.SerializedName

//AKA FullCycleExercise
data class NetworkCycleExercise(
    @SerializedName("exercise"    ) var exercise    : NetworkExercise,
    @SerializedName("order"       ) var order       : Int,
    @SerializedName("duration"    ) var duration    : Int,
    @SerializedName("repetitions" ) var repetitions : Int,
    @SerializedName("metadata"    ) var metadata    : ExerciseMetadata?   = null
){
    fun toCycleExercise():CycleExercise{
        return CycleExercise(
            name = exercise.name,
            image = exercise.metadata?.url?:"",
            order = order,
            time = duration,
            repetitions = repetitions
        )
    }
}
