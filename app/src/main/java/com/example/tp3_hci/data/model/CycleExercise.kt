package com.example.tp3_hci.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CycleExercise(
    val id: Int,
    val name:String,
    val image:String,
    val order: Int,
    var time:Int,
    var repetitions: Int,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)
