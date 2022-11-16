package com.example.tp3_hci.data.ui_state

//AKA RoutineCycle
data class RoutineCycleUiState(
    val name:String,
    val repetitions:Int,
    val exercises: List<ExerciseCardUiState>
)
