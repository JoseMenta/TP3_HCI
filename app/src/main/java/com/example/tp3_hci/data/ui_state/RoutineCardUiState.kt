package com.example.tp3_hci.data.ui_state

//AKA RoutineInfo
data class RoutineCardUiState(
    val id: Int,
    val name: String,
    val isFavourite: Boolean = false,
    val score: Int = 0,
    val tags: List<String>? = null,
    val imageUrl: String? = null
)
