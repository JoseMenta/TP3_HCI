package com.example.tp3_hci.data.model

import com.example.tp3_hci.data.ExerciseCardUiState

data class Cycle(
    val name:String,
    val repetitions:Int,
    var order: Int,
    val exercises: List<CycleExercise>
)
