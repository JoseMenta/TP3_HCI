package com.example.tp3_hci

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import coil.compose.AsyncImage
import com.example.tp3_hci.components.exercise.DialogRepetitions
import com.example.tp3_hci.components.exercise.DialogTime
import com.example.tp3_hci.data.model.CycleExercise
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.Shapes
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
    exercise: CycleExercise,
    status: ExerciseCardStatus = ExerciseCardStatus.VIEW_ONLY
) {
    var checkedState by remember { mutableStateOf(true) }
    var expandedState by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(true) }
    var repeat by remember { mutableStateOf(exercise.repetitions) }
    val sizeEditable = if (!expandedState) 272.dp else 362.dp
    val backgroundColor= backgroundColorCard(exercise.isSelected.value, checkedState )
    var time  by remember { mutableStateOf(exercise.time)}
    val minutes = if(time/60>=10){"${time/60}"}else{"0${time/60}"}
    val seconds = if(time%60>=10){"${time%60}"}else{"0${time%60}"}
    val display_time = if(time>60){"$minutes:$seconds"}else{"00:$seconds"}
    val alpha=if (checkedState) 1.0f else 0.5f
    CompositionLocalProvider(
        LocalContentAlpha provides
                if (checkedState) ContentAlpha.high else ContentAlpha.disabled
    ) {
        Card(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            shape = shape,
            elevation = elevation,
            border = border,
            modifier = modifier.combinedClickable (
                onLongClick = {if(status == ExerciseCardStatus.EDITABLE && checkedState){expandedState=!expandedState}},
                onClick = {/*TODO*/} )


        ) {
            Row(modifier = Modifier.animateContentSize(tween(durationMillis = 350, easing = LinearOutSlowInEasing))) {
                Column(
                    modifier =
                    Modifier
                        .width(sizeEditable)
                        .padding(start = 10.dp)
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text( // (3)
                            text = exercise.name,
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                        //if(status==ExerciseCardStatus.EDITABLE) {
                        if(!expandedState) {
                            Switch(
                                // (3)
                                checked = checkedState,
                                onCheckedChange = { checkedState = it }, // (4)
                                enabled = true,
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = FitiBlue,
                                    uncheckedThumbColor = Color(0xFF4E4E4E),
                                )
                            )
                        }else{
                            IconButton(onClick = {expandedState=!expandedState}) {
                                Icon(painter = rememberVectorPainter(Icons.Rounded.Check), contentDescription ="check" )
                            }
                        }
                    }
                    if(!expandedState){
                        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            if (time>0){
                                IconInfoExercise(icon = rememberVectorPainter(Icons.Rounded.Alarm), info = display_time, contentColor = contentColor.copy(alpha), background = backgroundColor)
                            }
                            if (repeat>0){
                                IconInfoExercise(icon = rememberVectorPainter(Icons.Rounded.Refresh), info = repeat.toString(),contentColor = contentColor.copy(alpha), background = backgroundColor)
                            }
                        }
                    } else {
                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically,) {
                            Column( verticalArrangement = Arrangement.spacedBy(7.dp),
                            ) {
                                var dialogTime by remember { mutableStateOf(false) }
                                var dialogRepetitions by remember { mutableStateOf(false) }
                                IconInfoExercise(icon = rememberVectorPainter(Icons.Rounded.Alarm),
                                    info = display_time,
                                    background = MaterialTheme.colors.primaryVariant,
                                    contentColor = Color.LightGray,
                                    modifier = Modifier.clickable { dialogTime = !dialogTime })

                                IconInfoExercise(icon = rememberVectorPainter(Icons.Rounded.Refresh),
                                    info = repeat.toString(),
                                    background = MaterialTheme.colors.primaryVariant,
                                    contentColor = Color.LightGray,
                                    modifier = Modifier.clickable {
                                        dialogRepetitions = !dialogRepetitions
                                    })
                                if (dialogTime) {
                                    DialogTime(setDialog = { dialogTime = it },
                                        setTime = { time = it },
                                        value = time)
                                }
                                if (dialogRepetitions) {
                                    DialogRepetitions(setDialog = { dialogRepetitions = it },
                                        setRepeat = { repeat = it },
                                        value = repeat)
                                }
                            }
                            Spacer(Modifier.width(110.dp))
                            AsyncImage(
                                model = exercise.image,
                                contentDescription = "exercise's image",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(120.dp)
                                    .alpha(alpha)
                                    .clip(Shapes.medium)
                            )
                        }
                    }
                }
                if (!expandedState){
                    AsyncImage(
                        model = exercise.image,
                        contentDescription = "exercise's image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .width(90.dp)
                            .height(93.dp)
                            .alpha(alpha)
                    )
                }
            }
        }
    }
}

@Composable
fun backgroundColorCard(selected:Boolean,checkedState:Boolean): Color {
    val color:Color =   if (!checkedState){
                         Color.Gray;
                        }else{
                            if(!selected){
                                MaterialTheme.colors.background
                            }else{
                                MaterialTheme.colors.onPrimary
                            }
                        }
    return color
}


@Composable
fun IconInfoExercise(   modifier: Modifier=Modifier,
                        icon: Painter,
                        info: String,
                        background: Color=MaterialTheme.colors.background,
                        contentColor: Color= contentColorFor(background)){
    Box(contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .background(color = background, shape = Shapes.medium)
            .height(45.dp)
            .width(90.dp)
            .padding(start = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(icon, contentDescription = null, tint = contentColor)
            Text(info, style = MaterialTheme.typography.h4, color = contentColor)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExerciseCardPreview() {
    TP3_HCITheme {
//        ExerciseCard(exercise = CycleExercise("Abdominales","https://media.tycsports.com/files/2022/09/28/484810/messi-vs-jamaica-foto-elsagetty-images_862x485.webp?v=1",120,30,0), status = ExerciseCardStatus.EDITABLE)
    }
}