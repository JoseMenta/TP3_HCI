package com.example.tp3_hci.components.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.data.view_model.ProfileViewModel
import com.example.tp3_hci.data.view_model.RatingViewModel
import com.example.tp3_hci.util.getViewModelFactory

@Preview
@Composable
fun justView(){
    RatingBar(Modifier, 3)
}


@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    viewModel: RatingViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = getViewModelFactory())
){
    var ratingState by remember {
        mutableStateOf(rating)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        for(i in 1..5){
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "start",
                modifier = modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clickable { ratingState = i
                        viewModel.changeRating(ratingState)},
                tint = if( i <= ratingState) Color.Black else Color.Gray,

            )
        }
    }
}