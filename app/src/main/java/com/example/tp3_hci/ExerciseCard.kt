package com.example.tp3_hci

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    border: BorderStroke? = null,
    background: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(background),
    shape: Shape = RoundedCornerShape(12.dp)
) {
    var checkedState by remember { mutableStateOf(true) }
    var expandedState by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf("") }
    var repeat by remember { mutableStateOf("") }
    CompositionLocalProvider(
        LocalContentAlpha provides
                if (checkedState) ContentAlpha.high else ContentAlpha.medium
    ) {
        Card(
            backgroundColor = background,
            contentColor = contentColor,
            shape = shape,
            elevation = elevation,
            border = border,
            modifier = modifier.combinedClickable (onLongClick = {expandedState=!expandedState}, onClick = {} )

        ) {
            Row() {
                Column(
                    modifier =
                    Modifier
                        .width(272.dp)
                        .padding(start = 12.dp)
                ) {
                    LabelledSwitch(
                        checked = checkedState,
                        label = "Abdominales",
                        onCheckedChange = { checkedState = it }
                    )
                    if(!expandedState)
                        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            IconInfoExercise(icon = painterResource(id = R.drawable.ic_baseline_alarm_24), info = "0:11")
                            IconInfoExercise(icon = rememberVectorPainter(Icons.Rounded.Refresh), info = "0:11")
                        } else{

                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            TextField(  label = { Text(text = "Tiempo") } ,
                                value =time,
                                onValueChange = {time=it},
                                leadingIcon = { Icon(  painter = painterResource(id = R.drawable.ic_baseline_alarm_24) ,
                                    contentDescription = "icon_time")
                                })
                            TextField(  label = { Text(text = "Repeticiones") },
                                value =repeat ,
                                onValueChange ={repeat=it},
                                leadingIcon = { Icon(  painter = rememberVectorPainter(Icons.Rounded.Refresh) ,
                                    contentDescription = "icon_repeat")
                                } )
                            IconInfoExercise(icon = painterResource(id = R.drawable.ic_baseline_alarm_24), info = "0:11")
                            IconInfoExercise(icon = rememberVectorPainter(Icons.Rounded.Refresh), info = "0:11")
                        }
                    }
                }
                val alphaImage=if (checkedState) 1.0f else 0.5f
                Image(
                    painter = painterResource(id = R.drawable.burpees),
                    contentDescription = null,
                    contentScale= ContentScale.FillBounds ,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .alpha(alphaImage)
                )
            }
        }
    }
}



@Composable
fun IconInfoExercise(icon: Painter, info: String){
    Box(
        modifier = Modifier
            .width(68.dp)
            .height(32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(icon, contentDescription = null)
            Text(info)
        }
    }
}