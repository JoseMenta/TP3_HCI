package com.example.tp3_hci

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.ui.theme.TP3_HCITheme

@Composable
fun RoutineExecutionCycle(
    cycle: RoutineCycle
){
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = cycle.name, style = MaterialTheme.typography.h3)
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(Icons.Outlined.Repeat,contentDescription = "Repeat icon")
                    Text(text = cycle.repetitions.toString(), style = MaterialTheme.typography.h3)
                }
            }
            cycle.exercises.forEach{ _ ->
                ExerciseCard(
                    modifier = Modifier.padding(8.dp,0.dp),
                    elevation = 4.dp,
                    background = MaterialTheme.colors.background,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
fun ExecuteRoutineGlobal(
    routine: Routine
){
    var selectedCycle by remember { mutableStateOf(0)}
    var selectedExercise by remember { mutableStateOf(0)}
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(4) { cycle ->
            val cycleSelected : Boolean = selectedCycle == cycle
            Card (
                modifier = Modifier.padding(4.dp),
                backgroundColor = MaterialTheme.colors.secondary
            ){
                Column {
                    for(exercise in 0..3){
                        val exerciseSelected:Boolean = selectedExercise== exercise
                        Card(
                            modifier = Modifier.padding(8.dp).clickable {
                                selectedCycle = cycle
                                selectedExercise = exercise
                            },
                            backgroundColor = if (selectedCycle==cycle && selectedExercise == exercise){
                                MaterialTheme.colors.onPrimary
                            }else{
                                MaterialTheme.colors.primary
                            }
                        ){
                            if(selectedCycle==cycle && selectedExercise == exercise){
                                Text(text = "Selected")
                            }else{
                                Text(text = "Not selected")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExecuteRoutineGlobalPreview() {
    TP3_HCITheme {
        ExecuteRoutineGlobal(Routine("Futbol",3,"Jose",3,120000,listOf("Hola","Como","estas", "buenas","tardes","Futbol","Scaloneta","Messi"), cycles))
    }
}