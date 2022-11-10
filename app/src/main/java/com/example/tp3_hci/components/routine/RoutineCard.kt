package com.example.tp3_hci.components.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tp3_hci.ui.theme.*

data class RoutineCard(
    val name: String,
    val isFavourite: Boolean = false,
    val score: Int = 0,
    val tags: List<String>? = null
)

@Composable
fun RoutineCard(
    routine : RoutineCard
){
    Card(
        elevation = 10.dp,
        shape = Shapes.medium,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(horizontal = 15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = "https://pbs.twimg.com/media/E5_Q6jQWUAEZKa4.jpg:large",
                contentDescription = "Messi",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.6f)
                    .background(FitiGreyImage.copy(alpha = 0.2f))
            )
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
                        text = routine.name,
                        style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
                        color = FitiBlueText
                    )

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    if(routine.isFavourite){
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Es favorita",
                            tint = FitiBlueFill,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "No es favorita",
                            tint = FitiBlueFill,
                        )
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

                    if (routine.tags != null) {
                        for(tag in routine.tags){
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

                    RatingStars(rating = routine.score)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TP3_HCITheme {
        Column(modifier = Modifier.padding(vertical = 20.dp)) {
            RoutineCard(
                name = "Futbol",
                // tags = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l")
                tags = listOf("Abdominales", "Piernas Fuertes", "Gemelos")
            )
        }

    }
}