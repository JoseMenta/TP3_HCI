package com.example.tp3_hci.data.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tp3_hci.data.CategoryItem
import com.example.tp3_hci.data.DifficultyItem
import com.example.tp3_hci.data.RatingItem
import com.example.tp3_hci.data.SearchByItem
import com.example.tp3_hci.data.ui_state.OnSearchUiState

class OnSearchViewModel() : ViewModel() {

    var onSearchUiState by mutableStateOf(
        OnSearchUiState(
            text = "",
            searchBy = SearchByItem.RoutineName,
            rating = null,
            difficulty = null,
            category = null
        )
    )

    // Actualiza el texto
    fun setText(newText : String){
        onSearchUiState = onSearchUiState.copy(
            text = newText
        )
    }

    // Actualiza el tipo de busqueda
    fun setSearchBy(newSearchBy : SearchByItem){
        if(newSearchBy != onSearchUiState.searchBy){
            onSearchUiState = onSearchUiState.copy(
                searchBy = newSearchBy
            )
        }
    }

    // Actualiza el filtro de busqueda por puntuacion
    fun setRating(newRating : RatingItem?){
        if(newRating != onSearchUiState.rating){
            onSearchUiState = onSearchUiState.copy(
                rating = newRating
            )
        }
    }

    // Actualiza el filtro de busqueda por dificultad
    fun setDifficulty(newDifficulty : DifficultyItem?){
        if(newDifficulty != onSearchUiState.difficulty){
            onSearchUiState = onSearchUiState.copy(
                difficulty = newDifficulty
            )
        }
    }


    // Actualiza el filtro de busqueda por dificultad
    fun setCategory(newCategory : CategoryItem?){
        if(newCategory != onSearchUiState.category){
            onSearchUiState = onSearchUiState.copy(
                category = newCategory
            )
        }
    }


    fun search(
        executeSearch : (String, Boolean, Int, Int, String?) -> Unit,
        onErrorSearch : ()->Unit
    ){
        if(onSearchUiState.text.length < 3){
            onErrorSearch()
        } else {
            executeSearch(
                onSearchUiState.text,
                onSearchUiState.searchBy == SearchByItem.RoutineName,
                if(onSearchUiState.rating == null) -1 else onSearchUiState.rating!!.times,
                if(onSearchUiState.difficulty == null) -1 else onSearchUiState.difficulty!!.times,
                onSearchUiState.category?.name
            )
        }
    }
}