package com.example.tp3_hci

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.components.routine.DifficultyIcons
import com.example.tp3_hci.components.routine.RatingStars
import com.example.tp3_hci.components.routine.RoutineImage
import com.example.tp3_hci.components.routine.RoutineTag
import com.example.tp3_hci.data.ExerciseCardUiSate
import com.example.tp3_hci.data.RoutineDetailUiState
import com.example.tp3_hci.data.RoutineCycleUiState
import com.example.tp3_hci.ui.theme.Shapes
import com.example.tp3_hci.ui.theme.TP3_HCITheme

val names = listOf("Futbol","Scaloneta")



val exercises = listOf(
    ExerciseCardUiSate("Cardio","https://e00-ar-marca.uecdn.es/claro/assets/multimedia/imagenes/2022/10/08/16652315741032.jpg",10,20),
    ExerciseCardUiSate("Running","https://e00-ar-marca.uecdn.es/claro/assets/multimedia/imagenes/2022/10/08/16652315741032.jpg",100,30),
    ExerciseCardUiSate("Abdominales","https://e00-ar-marca.uecdn.es/claro/assets/multimedia/imagenes/2022/10/08/16652315741032.jpg",0,40),
    ExerciseCardUiSate("Pecho plano","https://e00-ar-marca.uecdn.es/claro/assets/multimedia/imagenes/2022/10/08/16652315741032.jpg",0,20)
)
val cycles = listOf(
    RoutineCycleUiState("Calentamiento",2, exercises),
    RoutineCycleUiState("Ciclo 1",4, exercises),
    RoutineCycleUiState("Ciclo 2",3, exercises),
    RoutineCycleUiState("Enfriamiento",3, exercises)
)

@Composable
fun RoutineData(
    name:String,
    difficulty: Int,
    creator: String,
    rating: Int,
    votes: Int
    ){
        val thousand_votes = votes/1000.0
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ){
            Text(text = name, style = MaterialTheme.typography.h1)
            Row{
                Text(text = stringResource(R.string.difficulty), style = MaterialTheme.typography.h4)
                DifficultyIcons(difficulty = difficulty)
            }
            Text(text = "${stringResource(R.string.created_by)}: $creator",style = MaterialTheme.typography.h4)
            Row{
                RatingStars(rating = rating)
                Text(text = "($thousand_votes K)")
            }

        }
}
@Composable
fun RoutineTags(
    tags: List<String>,
    modifier:Modifier = Modifier
){
    LazyRow(modifier = modifier){
        items(tags){tag->
            RoutineTag(
                text = tag,
                modifier = Modifier
                    .padding(3.dp)
                    .clip(Shapes.medium)
            )
        }
    }
}


@Composable
fun RoutineCycle(
    cycle: RoutineCycleUiState,
    status: ExerciseCardStatus = ExerciseCardStatus.VIEW_ONLY
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
            cycle.exercises.forEach{ exercise ->
                ExerciseCard(
                    modifier = Modifier.padding(8.dp,0.dp),
                    elevation = 4.dp,
                    background = MaterialTheme.colors.background,
                    status = status,
                    exercise = exercise
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
fun RoutineDetail(
    routine: RoutineDetailUiState
){
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(8.dp),
                text = {Text(stringResource(id = R.string.start), color = Color.White, style = MaterialTheme.typography.h4)},
                icon = {Icon(Icons.Outlined.PlayArrow,"Play arrow",tint = Color.White)},
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.medium,
                backgroundColor = MaterialTheme.colors.onPrimary
        )
        },
        floatingActionButtonPosition = FabPosition.Center
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = it
        ){
            item{
                Row(
                    modifier = Modifier
                        .padding(8.dp, 8.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    RoutineData(name = routine.name, difficulty = routine.difficulty ,creator = routine.creator, rating = routine.rating , votes = routine.votes )
                    RoutineImage(
                        source = "https://media.tycsports.com/files/2022/09/28/484810/messi-vs-jamaica-foto-elsagetty-images_862x485.webp?v=1",
                        contentDescription = "Routine Image",
                        modifier = Modifier
                            .clip(Shapes.medium)
                            .size(140.dp)
                            .aspectRatio(1f / 1f)
                    )
                }
            }
            item {
                RoutineTags(tags = routine.tags, modifier = Modifier.padding(8.dp,0.dp))
            }
            items(routine.cycles){
                RoutineCycle(it, status = ExerciseCardStatus.EDITABLE)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutineDetailPreview() {
    TP3_HCITheme {
        RoutineDetail(RoutineDetailUiState("Futbol",3,"Jose",3,120000,listOf("Hola","Como","estas", "buenas","tardes","Futbol","Scaloneta","Messi"), cycles))
    }
}