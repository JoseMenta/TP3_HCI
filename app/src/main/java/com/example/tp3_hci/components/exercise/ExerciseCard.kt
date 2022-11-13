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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tp3_hci.data.ExerciseCardUiSate
import com.example.tp3_hci.data.RoutineDetailUiState
import com.example.tp3_hci.ui.theme.TP3_HCITheme


enum class ExerciseCardStatus{
    EDITABLE,
    SELECTABLE,
    VIEW_ONLY
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    border: BorderStroke? = null,
    background: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(background),
    shape: Shape = RoundedCornerShape(12.dp),
    exercise: ExerciseCardUiSate,
    status: ExerciseCardStatus = ExerciseCardStatus.VIEW_ONLY
) {
    var checkedState by remember { mutableStateOf(true) }
    var expandedState by remember { mutableStateOf(false) }
    var selected = false
    var checked by remember { mutableStateOf(true) }
    var repeat by remember { mutableStateOf("") }
    val minutes = if(exercise.time/60>=10){"${exercise.time/60}"}else{"0${exercise.time/60}"}
    val seconds = if(exercise.time%60>=10){"${exercise.time%60}"}else{"0${exercise.time%60}"}
    val display_time = if(exercise.time>60){"$minutes:$seconds"}else{"00:$seconds"}
    CompositionLocalProvider(
        LocalContentAlpha provides
                if (checkedState) ContentAlpha.high else ContentAlpha.medium
    ) {
        Card(
            backgroundColor = if(!selected){MaterialTheme.colors.background}else{MaterialTheme.colors.onPrimary},
            contentColor = contentColor,
            shape = shape,
            elevation = elevation,
            border = border,
            modifier = modifier.combinedClickable (
                onLongClick = {if(status == ExerciseCardStatus.EDITABLE && checkedState){expandedState=!expandedState}},
                onClick = {if (status==ExerciseCardStatus.SELECTABLE){selected = true}} )

        ) {
            Row() {
                Column(
                    modifier =
                    Modifier
                        .width(272.dp)
                        .padding(start = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text( // (3)
                            text = exercise.name,
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(end = 16.dp)
                        )
                        if(status==ExerciseCardStatus.EDITABLE) {
                            Switch(
                                // (3)
                                checked = checkedState,
                                onCheckedChange = { checkedState = it }, // (4)
                                enabled = true,
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color(0xFF27496D),
                                    uncheckedThumbColor = Color(0xFF4E4E4E),
                                )
                            )
                        }
                    }
//                    LabelledSwitch(
//                        checked = checkedState,
//                        label = exercise.name,
//                        onCheckedChange = { checkedState = it }
//                    )
                    if(!expandedState)
                        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            IconInfoExercise(icon = painterResource(id = R.drawable.ic_baseline_alarm_24), info = display_time)
                            IconInfoExercise(icon = rememberVectorPainter(Icons.Rounded.Refresh), info = exercise.repetitions.toString())
                        } else{

                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            TextField(  label = { Text(text = stringResource(id = R.string.time)) } ,
                                value =display_time,
                                onValueChange = {/*TODO: que cambie al repositorio*/ },
                                leadingIcon = { Icon(  painter = painterResource(id = R.drawable.ic_baseline_alarm_24) ,
                                    contentDescription = "icon_time")
                                })
                            TextField(  label = { Text(text = stringResource(id = R.string.repetitions)) },
                                value =repeat ,
                                onValueChange ={/*TODO: que cambie al repositorio*/ },
                                leadingIcon = { Icon(  painter = rememberVectorPainter(Icons.Rounded.Refresh) ,
                                    contentDescription = "icon_repeat")
                                } )
                            IconInfoExercise(icon = painterResource(id = R.drawable.ic_baseline_alarm_24), info = display_time )
                            IconInfoExercise(icon = rememberVectorPainter(Icons.Rounded.Refresh), info = exercise.repetitions.toString())
                        }
                    }
                }
                val alphaImage=if (checkedState) 1.0f else 0.5f
                AsyncImage(
                    model = exercise.image,
                    contentDescription = "exercise's image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(80.dp).alpha(alphaImage)
                )
//                Image(
//                    painter = painterResource(id = R.drawable.burpees),
//                    contentDescription = null,
//                    contentScale= ContentScale.FillBounds ,
//                    modifier = Modifier
//                        .width(80.dp)
//                        .height(80.dp)
//                        .alpha(alphaImage)
//                )
            }
        }
    }
}



@Composable
fun IconInfoExercise(icon: Painter, info: String){
    Box(
        modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(icon, contentDescription = null)
            Text(info, style = MaterialTheme.typography.h4)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExerciseCardPreview() {
    TP3_HCITheme {
        ExerciseCard(exercise = ExerciseCardUiSate("Abdominales","https://media.tycsports.com/files/2022/09/28/484810/messi-vs-jamaica-foto-elsagetty-images_862x485.webp?v=1",120,30,), status = ExerciseCardStatus.EDITABLE)
    }
}