package com.example.tp3_hci.data.ui_state

//AKA CycleExercise
data class ExerciseCardUiState(
    val name:String,
    val image:String,
    val time:Int,
    val repetitions: Int,
    val selected: Boolean = false
)
