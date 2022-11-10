package com.example.tp3_hci.components.routine

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.runtime.Composable

@Composable
fun DifficultyIcons(
    difficulty: Int
){
    for(i in 1..difficulty){
        Icon(Icons.Outlined.Bolt, contentDescription = "Rating" )
    }
}