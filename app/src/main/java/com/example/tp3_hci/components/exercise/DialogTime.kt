package com.example.tp3_hci.components.exercise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.tp3_hci.ui.theme.Shapes
import java.time.LocalTime

@Composable
fun DialogTime(setDialog: (Boolean) -> Unit, setTime:(Int)->Unit, value:Int){
    Dialog(onDismissRequest = { setDialog(false)}) {
        var newTime by remember { mutableStateOf(value) }
        Surface(shape= Shapes.medium, color = MaterialTheme.colors.background) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Selecionar Tiempo",style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(20.dp))
                    WheelTimePicker(startTime = LocalTime.of(0,value/60,value%60),
                        onScrollFinished = { snappedTime -> newTime=(60*snappedTime.minute).plus(snappedTime.second) })

                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton( onClick = { setDialog(false)
                        setTime(newTime)},
                        shape = Shapes.medium,
                        border= BorderStroke( 1.dp, MaterialTheme.colors.primary),
                        elevation = ButtonDefaults.elevation(4.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = MaterialTheme.colors.primary)

                    ) {
                        Text(text = "Hecho", style = MaterialTheme.typography.h4)
                    }
                }
            }
        }
    }
}