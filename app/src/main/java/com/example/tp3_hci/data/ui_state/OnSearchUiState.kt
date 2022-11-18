package com.example.tp3_hci.data.ui_state

import com.example.tp3_hci.data.CategoryItem
import com.example.tp3_hci.data.DifficultyItem
import com.example.tp3_hci.data.RatingItem
import com.example.tp3_hci.data.SearchByItem

data class OnSearchUiState(
    val text : String,
    val searchBy : SearchByItem,
    val rating : RatingItem?,
    val difficulty : DifficultyItem?,
    val category: CategoryItem?
)