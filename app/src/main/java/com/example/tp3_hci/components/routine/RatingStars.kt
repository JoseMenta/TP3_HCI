package com.example.tp3_hci.components.routine

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import com.example.tp3_hci.ui.theme.FitiBlueFill

@Composable
fun RatingStars(
    rating: Int,
){
    for(i in 1..rating){
        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = "Rating",
            tint = FitiBlueFill,
        )
    }
    for(i in rating+1 .. 5){
        Icon(
            imageVector = Icons.Outlined.StarOutline,
            contentDescription = "Rating",
            tint = FitiBlueFill,
        )
    }
}