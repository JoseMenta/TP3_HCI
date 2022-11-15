package com.example.tp3_hci.data


//AKA CycleExercise
data class ExerciseCardUiSate(
    val name:String,
    val image:String,
    val time:Int,
    val repetitions: Int,
    val selected: Boolean = false
)
//AKA RoutineCycle
data class RoutineCycleUiState(
    val name:String,
    val repetitions:Int,
    val exercises: List<ExerciseCardUiSate>
)
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
//AKA RoutineInfo
data class RoutineCardUiState(
    val id: Int,
    val name: String,
    val isFavourite: Boolean = false,
    val score: Int = 0,
    val tags: List<String>? = null,
    val imageUrl: String? = null
)
