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
@Composable
fun DialogRepetitions(setDialog: (Boolean) -> Unit, setRepeat:(Int)->Unit, value:Int){
    Dialog(onDismissRequest = { setDialog(false)}) {
        var newRepeat by remember { mutableStateOf(value) }
        Surface(shape= Shapes.medium, color = MaterialTheme.colors.background) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Selecionar Repeticiones",style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(20.dp))
                    WheelTextPicker(texts = (0..99).map { "$it" },
                        startIndex = newRepeat,
                        onScrollFinished = { selectIndex -> newRepeat=selectIndex
                            return@WheelTextPicker selectIndex})
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton( onClick = { setDialog(false)
                                            setRepeat(newRepeat)},
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