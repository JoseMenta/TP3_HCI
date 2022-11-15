package com.example.tp3_hci.data.ui_state

//AKA Routine
data class RoutineDetailUiState(
    val id: Int,
    val name:String,
    val difficulty: Int,
    val creator:String,
    val rating:Int,
    val votes:Int,
    val tags: List<String>,
    val cycles: List<RoutineCycleUiState>
)
