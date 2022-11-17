package com.example.tp3_hci.data.model

data class CycleExercise(
    val id: Int,
    val name:String,
    val image:String,
    val order: Int,
    val time:Int,
    val repetitions: Int,
    var isSelected: Boolean = false
)
