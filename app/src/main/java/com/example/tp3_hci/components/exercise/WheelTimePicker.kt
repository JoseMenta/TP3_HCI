package com.example.tp3_hci.components.exercise

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    disablePastTime: Boolean = false,
    size: DpSize = DpSize(128.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.h4,
    textColor: Color = LocalContentColor.current,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(12.dp),
    selectorColor: Color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colors.primary),
    onScrollFinished : (snappedTime: LocalTime) -> Unit = {}
) {
    val minuteTexts: List<String> = (0..59).map { it.toString().padStart(2, '0') }
    val selectedMinute = remember { mutableStateOf(startTime.minute) }

    val secondTexts: List<String> = (0..59).map { it.toString().padStart(2, '0') }
    val selectedSecond = remember { mutableStateOf(startTime.second) }

    Box(modifier = modifier, contentAlignment = Alignment.Center){
        if(selectorEnabled){
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / 3),
                shape = selectorShape,
                color = selectorColor,
                border = selectorBorder
            ) {}
        }
        Row {
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = minuteTexts,
                textStyle = textStyle,
                textColor = textColor,
                startIndex = startTime.minute,
                selectorEnabled = false,
                onScrollFinished = { selectedIndex ->
                    try {
                        val selectedTime = LocalTime.of(0,selectedIndex, selectedSecond.value)
                        val isTimeBefore = isTimeBefore(selectedTime, startTime)

                        if(disablePastTime){
                            if(!isTimeBefore){
                                selectedMinute.value = selectedIndex
                            }
                        }else{
                            selectedMinute.value = selectedIndex
                        }

                        onScrollFinished(
                            LocalTime.of(0,
                                selectedMinute.value,
                                selectedSecond.value
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                    return@WheelTextPicker selectedMinute.value
                }
            )
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = secondTexts,
                textStyle = textStyle,
                textColor = textColor,
                startIndex = startTime.second,
                selectorEnabled = false,
                onScrollFinished = { selectedIndex ->
                    try {
                        val selectedTime = LocalTime.of(0,selectedMinute.value, selectedIndex)
                        val isTimeBefore = isTimeBefore(selectedTime, startTime)

                        if(disablePastTime){
                            if(!isTimeBefore){
                                selectedSecond.value = selectedIndex
                            }
                        }else{
                            selectedSecond.value = selectedIndex
                        }

                        onScrollFinished(
                            LocalTime.of(0,
                                selectedMinute.value,
                                selectedSecond.value
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                    return@WheelTextPicker selectedSecond.value
                }
            )
        }
        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ":",
                style = textStyle,
                color = textColor
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isTimeBefore(time: LocalTime, currentTime: LocalTime): Boolean{
    return time.isBefore(currentTime)
}