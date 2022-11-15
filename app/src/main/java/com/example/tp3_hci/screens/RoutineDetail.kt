package com.example.tp3_hci.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.ExerciseCard
import com.example.tp3_hci.ExerciseCardStatus
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.routine.DifficultyIcons
import com.example.tp3_hci.components.routine.RatingStars
import com.example.tp3_hci.components.routine.RoutineImage
import com.example.tp3_hci.components.routine.RoutineTag
import com.example.tp3_hci.data.ExerciseCardUiSate
import com.example.tp3_hci.data.RoutineDetailUiState
import com.example.tp3_hci.data.RoutineCycleUiState
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.ui.theme.Shapes
import com.example.tp3_hci.utilities.TopAppBarType
import com.example.tp3_hci.utilities.navigation.RoutineDetailNavigation



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
private fun RoutineData(
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
private fun RoutineTags(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineDetail(
    routineDetailNavigation: RoutineDetailNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    routine: RoutineDetailUiState,
    srcImg: String
){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    setTopAppBar(
        TopAppBarType(
            topAppBar = {
                TopAppBar(
                    routine = routine,
                    scrollBehavior = scrollBehavior,
                    routineDetailNavigation = routineDetailNavigation
                )
            }
        )
    )

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(8.dp),
                text = {Text(stringResource(id = R.string.start), color = Color.White, style = MaterialTheme.typography.h4)},
                icon = {Icon(Icons.Outlined.PlayArrow,"Play arrow",tint = Color.White)},
                onClick = {
                    routineDetailNavigation.getExecuteRoutineScreen().invoke("${routine.id}")
                },
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
                        source = srcImg,
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    routineDetailNavigation: RoutineDetailNavigation,
    routine: RoutineDetailUiState,
){
    val clipboardManager: androidx.compose.ui.platform.ClipboardManager =
        LocalClipboardManager.current

    TopNavigationBar(
        scrollBehavior = scrollBehavior,
        leftIcon = {
            IconButton(onClick = {
                routineDetailNavigation.getPreviousScreen().invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.search),
                    tint = FitiWhiteText,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        centerComponent = {
            Text(
                text = stringResource(id = R.string.fiti),
                style = MaterialTheme.typography.h2,
                color = FitiWhiteText
            )
        },
        secondRightIcon = {
            IconButton(onClick = {
                clipboardManager.setText(AnnotatedString( "https://fiti.com/RoutineDetails/${routine.id}"))
            }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(id = R.string.search),
                    tint = FitiWhiteText,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        rightIcon = {
            IconButton(onClick = {
                /* TODO */
            }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(id = R.string.search),
                    tint = FitiWhiteText,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

/*
@Preview(showBackground = true)
@Composable
fun RoutineDetailPreview() {
    TP3_HCITheme {
        RoutineDetail(RoutineDetailUiState("Futbol",3,"Jose",3,120000,listOf("Hola","Como","estas", "buenas","tardes","Futbol","Scaloneta","Messi"), cycles))
    }
}
*/
