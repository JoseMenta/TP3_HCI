package com.example.tp3_hci.components.routine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tp3_hci.ui.theme.*
import com.example.tp3_hci.R
import com.example.tp3_hci.data.model.RoutineOverview
import com.example.tp3_hci.utilities.navigation.RoutineCardNavigation


@Composable
fun FullRoutineCard(
    routine : MutableState<RoutineOverview>,
    modifier: Modifier = Modifier,
    routineCardNavigation: RoutineCardNavigation,
    onFavoriteChange : (MutableState<RoutineOverview>)->Unit
){
    Card(
        elevation = 10.dp,
        shape = Shapes.medium,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .heightIn(max = 180.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    routineCardNavigation.getRoutineDetailScreen().invoke("${routine.value.id }")
                },
                modifier = Modifier.weight(0.6f),
                contentPadding = PaddingValues(0.dp)
            ) {
                if(routine.value.imageUrl == null){
                    Image(
                        painter = painterResource(id = R.drawable.image_placeholder),
                        contentDescription = stringResource(id = R.string.routine_no_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(FitiGreyImage.copy(alpha = 0.2f))
                    )
                } else {
                    AsyncImage(
                        model = routine.value.imageUrl,
                        contentDescription = stringResource(id = R.string.routine_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(FitiGreyImage.copy(alpha = 0.2f))
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.4f)
                    .background(FitiGreyImage)
                    .padding(horizontal = 10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.
                    padding(top = 5.dp)
                ) {
                    Text(
                        text = routine.value.name,
                        style = MaterialTheme.typography.h3.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = FitiBlueText
                    )

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { onFavoriteChange(routine) },
                        modifier = Modifier.size(20.dp)
                    ) {
                        if(routine.value.isFavourite){
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = stringResource(id = R.string.routine_is_favorite),
                                tint = FitiBlueFill,
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.routine_is_not_favorite),
                                tint = FitiBlueFill,
                            )
                        }
                    }

                }
                Row(
                    modifier = Modifier.
                    padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val maxTagsLength : Int = 20
                    var tagsLengthUsed : Int = 0

                    val maxTagsPrinted : Int = 2
                    var tagsPrinted : Int = 0
                    var tagsNotPrinted : Int = 0

                    if (routine.value.tags != null) {
                        for(tag in routine.value.tags!!){
                            if(tag.length + tagsLengthUsed < maxTagsLength && tagsPrinted < maxTagsPrinted){
                                tagsLengthUsed += tag.length
                                tagsPrinted++
                                RoutineTag(
                                    text = tag,
                                    modifier = Modifier.
                                        padding(end = 5.dp)
                                )
                            } else {
                                tagsNotPrinted++
                            }
                        }
                        if(tagsNotPrinted > 0){
                            RoutineTag(text = "+$tagsNotPrinted")
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    RatingStars(
                        rating = routine.value.score,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun RoutineCard(
    routine : MutableState<RoutineOverview>,
    modifier: Modifier = Modifier,
    routineCardNavigation: RoutineCardNavigation,
    onFavoriteChange : (MutableState<RoutineOverview>)->Unit,
    simplify: Boolean
){
    if(simplify){
        SimplyRoutineCard(
            routine = routine,
            modifier = modifier,
            routineCardNavigation = routineCardNavigation,
            onFavoriteChange = onFavoriteChange,
        )
    }else{
        FullRoutineCard(
            routine = routine,
            modifier = modifier,
            routineCardNavigation = routineCardNavigation,
            onFavoriteChange = onFavoriteChange,
        )
    }
}


@Composable
fun SimplyRoutineCard(
    routine : MutableState<RoutineOverview>,
    modifier: Modifier = Modifier,
    routineCardNavigation: RoutineCardNavigation,
    onFavoriteChange : (MutableState<RoutineOverview>)->Unit
){
    Card(
        elevation = 5.dp,
        shape = Shapes.medium,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .heightIn(max = 180.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    routineCardNavigation.getRoutineDetailScreen().invoke("${routine.value.id}")
                },
                modifier = Modifier.weight(0.6f),
                contentPadding = PaddingValues(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.4f)
                        .background(FitiGreyImage)
                        .padding(horizontal = 10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.
                        padding(top = 5.dp)
                    ) {
                        Column(){
                            Text(
                                text = routine.value.name,
                                style = MaterialTheme.typography.h3.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                ),
                                color = FitiBlueText,
                                modifier = Modifier.padding(bottom =  10.dp)
                            )
                            Row(
                                modifier = Modifier.padding(bottom =  10.dp)
                            ){
                                RatingStars(
                                    rating = routine.value.score,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                        }

                        Spacer(
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = { onFavoriteChange(routine) },
                            modifier = Modifier.size(30.dp)
                        ) {
                            if(routine.value.isFavourite){
                                Icon(
                                    imageVector = Icons.Outlined.Favorite,
                                    contentDescription = stringResource(id = R.string.routine_is_favorite),
                                    tint = FitiBlueFill,
                                    modifier = Modifier.size(30.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = stringResource(id = R.string.routine_is_not_favorite),
                                    tint = FitiBlueFill,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TP3_HCITheme {
        Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 0.dp)) {
            RoutineCard(
                RoutineCardUiState(name = "Futbol", tags = listOf("Abdominales", "Piernas Fuertes", "Gemelos"))
                // RoutineInfo(name = "Futbol", listOf("Abdominales", "Piernas Fuertes", "Gemelos"))
            )
        }

    }
}
 */