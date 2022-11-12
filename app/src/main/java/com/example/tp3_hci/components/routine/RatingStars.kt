package com.example.tp3_hci.components.routine

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_hci.ui.theme.FitiBlueFill

@Composable
fun RatingStars(
    rating: Int,
    modifier : Modifier = Modifier
){
    for(i in 1..rating){
        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = "Rating",
            tint = FitiBlueFill,
            modifier = modifier
        )
    }
    for(i in rating+1 .. 5){
        Icon(
            imageVector = Icons.Outlined.StarOutline,
            contentDescription = "Rating",
            tint = FitiBlueFill,
            modifier = modifier
        )
    }
}