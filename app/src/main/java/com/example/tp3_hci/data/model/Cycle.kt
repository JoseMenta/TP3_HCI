package com.example.tp3_hci.data.model

import androidx.compose.runtime.MutableState

data class Cycle(
    val name:String,
    val repetitions:Int,
    var order: Int,
    val exercises: List<CycleExercise>
)
