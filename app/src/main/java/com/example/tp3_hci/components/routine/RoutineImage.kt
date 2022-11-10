package com.example.tp3_hci.components.routine

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun RoutineImage(
    source:String,
    contentDescription: String,
    modifier: Modifier = Modifier
){
    AsyncImage(
        model = source,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}