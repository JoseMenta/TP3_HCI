package com.example.tp3_hci.data.model

import java.util.Date

data class RoutineOverview(
    val id: Int,
    val name: String,
    val isFavourite: Boolean = false,
    val score: Int = 0,
    val creationDate: Date,
    val difficulty: Int,
    val category: Category,
    val tags: List<String>? = null,
    val imageUrl: String? = null
)
