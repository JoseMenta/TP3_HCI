package com.example.tp3_hci.data.model



data class RoutineDetail (
    val id: Int,
    val name:String,
    val difficulty: Int,
    val creator:String,
    val rating:Int,
    val votes:Int,
    val isFavourite: Boolean,
    val tags: List<String>,
    val cycles: List<Cycle>,
    val imageUrl: String
)