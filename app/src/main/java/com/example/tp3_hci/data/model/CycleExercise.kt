package com.example.tp3_hci.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CycleExercise(
    val id: Int,
    val name:String,
    val image:String,
    val order: Int,
    val time:Int,
    val repetitions: Int,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)
