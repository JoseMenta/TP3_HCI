package com.example.tp3_hci.screens


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.animation.core.Animatable

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tp3_hci.ExerciseCardStatus
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.state_holders.RoutineDetail.ExecuteRoutineViewModel
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.ui.theme.Shapes
import com.example.tp3_hci.utilities.TopAppBarType
import com.example.tp3_hci.utilities.navigation.ExecuteRoutineNavigation
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp3_hci.data.model.CycleExercise
import com.example.tp3_hci.data.model.RoutineDetail
import com.example.tp3_hci.util.getViewModelFactory
import kotlinx.coroutines.launch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import com.example.tp3_hci.utilities.ErrorSnackBar


@Composable
private fun CountdownTimer(
    exercise: MutableState<CycleExercise>,
    modifier: Modifier = Modifier,
    paused : Boolean,
    num: MutableState<Int>,
    onTimeFinished: ()->Unit,
){
    var paused_curr = paused
    var value by remember { mutableStateOf(1f) }
    var currentTime by remember { mutableStateOf(exercise.value.time) }
    val animateFloat = remember { Animatable(1f) }
    var minutes = if(currentTime/60>=10){"${currentTime/60}"}else{"0${currentTime/60}"}
    var seconds = if(currentTime%60>=10){"${currentTime%60}"}else{"0${currentTime%60}"}
    var display_time = if(currentTime>60){"$minutes:$seconds"}else{"00:$seconds"}
    LaunchedEffect(key1 = exercise.value, key2 = num.value){
        value = 1f
        currentTime = exercise.value.time
    }
    LaunchedEffect(value) {
        animateFloat.animateTo(
            targetValue = value,
            animationSpec = tween(durationMillis = 300, easing = LinearEasing))
    }
    LaunchedEffect(key1 = currentTime , key2 = paused){
        //cada vez que cambia key, ejecuta este codigo
        if(currentTime>0 && !paused_curr){
            delay(1000L)
            currentTime-=1
            value = currentTime/exercise.value.time.toFloat()
            minutes = if(currentTime/60>=10){"${currentTime/60}"}else{"0${currentTime/60}"}
            seconds = if(currentTime%60>=10){"${currentTime%60}"}else{"0${currentTime%60}"}
            display_time = if(currentTime>60){"$minutes:$seconds"}else{"00:$seconds"}
            if(currentTime==0){
                onTimeFinished()
            }
        }
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(100.dp)
            .clickable(
                onClick = { }
            ),
        contentAlignment = Alignment.Center
    ){
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .size(size = 300.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            drawArc(
                color = Color(0xFF00909E),
                startAngle = -90f,
                sweepAngle = animateFloat.value*360f,
                useCenter = true
            )
        }
        Text(text = display_time, color = Color.White, style = MaterialTheme.typography.h2)
    }
}

@Composable
private fun CountdownRepetitions(
    modifier: Modifier = Modifier,
    repetitions:Int
){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(100.dp)
            .background(MaterialTheme.colors.primaryVariant)
    ){
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(Icons.Outlined.Replay, contentDescription = "Repetitions",Modifier.size(36.dp), tint = Color.White)
            Text(text = repetitions.toString(), style = MaterialTheme.typography.h2, color = Color.White)
        }
    }
}

@Composable
private fun ExecutionControls(
    executeRoutineNavigation: ExecuteRoutineNavigation,
//    time:Long?,
//    id: Int,
//    repetitions: Int?,
    onPrevTouched: ()->Unit,
    onNextTouched: ()->Unit,
    exercise: MutableState<CycleExercise>,
    exerciseNumber: MutableState<Int>,
    hasPrevExercise: Boolean = true
){
    var paused by remember { mutableStateOf(false) }
    var num by remember { mutableStateOf(0) }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            if(exercise.value.time!=0){
                CountdownTimer(paused = paused, onTimeFinished = onNextTouched, exercise = exercise,num = exerciseNumber)
            }
            if(exercise.value.repetitions!=0){
                CountdownRepetitions(repetitions = exercise.value.repetitions)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .size(50.dp)
                    .background(MaterialTheme.colors.onPrimary),
                onClick = onPrevTouched,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary),
                enabled = hasPrevExercise
            ) {
                Icon( Icons.Outlined.SkipPrevious, contentDescription = "previous", tint = Color.White)
            }
            Button(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .size(80.dp),
                onClick = { paused = !paused },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
            ) {
                if(!paused){
                    Icon(Icons.Outlined.Pause, contentDescription = "pause", tint = Color.White)
                }else {
                    Icon(Icons.Outlined.PlayArrow, contentDescription = "play", tint = Color.White)
                }
            }
            Button(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .size(50.dp),
                onClick = onNextTouched,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
            ) {
                Icon( Icons.Outlined.SkipNext, contentDescription = "next", tint = Color.White)
            }
        }
    }
}

@Composable
private fun ExecuteRoutineExerciseDetail(
    executeRoutineNavigation: ExecuteRoutineNavigation,
    exercise: MutableState<CycleExercise>,
    expanded: Boolean = true,
    onNextTouched: () -> Unit,
    onPrevTouched: () -> Unit,
    exerciseNumber: MutableState<Int>,
    hasPrevExercise: Boolean = true
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        if(expanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Text(exercise.value.name, style = MaterialTheme.typography.h1)
            }
            AsyncImage(
                model = exercise.value.image,
                contentDescription = "exercise image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(Shapes.large)
                    .aspectRatio(16 / 9f)
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
        ExecutionControls(
            exercise = exercise,
            executeRoutineNavigation = executeRoutineNavigation,
            onNextTouched = onNextTouched,
            onPrevTouched = onPrevTouched,
            exerciseNumber = exerciseNumber,
            hasPrevExercise = hasPrevExercise
        )
    }
}

@Composable
private fun ExecuteRoutineGlobal(
    routine: RoutineDetail,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ){
        items(routine.cycles){cycle->
            RoutineCycle(
                cycle = cycle,
                status = ExerciseCardStatus.SELECTABLE
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExecuteRoutine(
    executeRoutineNavigation: ExecuteRoutineNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    routineId: Int,
    viewModel: ExecuteRoutineViewModel = viewModel(factory = getViewModelFactory())
){
    var returned by remember{ mutableStateOf(true)}
    val uiState = viewModel.uiState
    if(!uiState.isFetching && uiState.routine==null && uiState.message==null){
        viewModel.getRoutine(routineId)
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    if(returned) {
        returned = false
        setTopAppBar(
            TopAppBarType(
                topAppBar = {
                    TopAppBar(
                        scrollBehavior = scrollBehavior,
                        title = stringResource(id = R.string.execute_routine),
                        executeRoutineNavigation = executeRoutineNavigation
                    )
                }
            )
        )
    }

    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val compressed = ((sheetState.progress.from==sheetState.progress.to && sheetState.progress.to==BottomSheetValue.Collapsed)
            || (sheetState.progress.from==BottomSheetValue.Collapsed && sheetState.progress.fraction<=0.4)
            || (sheetState.progress.from==BottomSheetValue.Expanded && sheetState.progress.fraction>0.6 && sheetState.progress.fraction!=1f))
    val scope = rememberCoroutineScope()
    if(!uiState.isFetching) {
        if(uiState.routine!=null && uiState.selectedExercise!=null) {
            BottomSheetScaffold(
                sheetContent = {
                    //contenido de la vista de abajo
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    scope.launch {
                                        if (sheetState.isCollapsed) {
                                            sheetState.expand()
                                        } else {
                                            sheetState.collapse()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                            ) {
                                if (sheetState.isCollapsed) {
                                    Icon(
                                        Icons.Outlined.ExpandLess,
                                        contentDescription = "open detail"
                                    )
                                } else {
                                    Icon(
                                        Icons.Outlined.ExpandMore,
                                        contentDescription = "close detail"
                                    )
                                }
                            }
                            ExecuteRoutineExerciseDetail(
                                exercise = uiState.selectedExercise,
                                expanded = !compressed,
                                onPrevTouched = { viewModel.prevExercise() },
                                onNextTouched = {
                                    if (!viewModel.hasNextExercise()) {
                                        viewModel.addRoutineExecution(routineId)
                                        returned = true
                                        executeRoutineNavigation.getRateRoutineScreen()
                                            .invoke("$routineId")
                                    } else {
                                        viewModel.nextExercise()
                                    }
                                },
                                executeRoutineNavigation = executeRoutineNavigation,
                                exerciseNumber = uiState.exerciseNumber,
                                hasPrevExercise = uiState.hasPrevExercise
                            )
                        }
                    }
                },
                sheetShape = MaterialTheme.shapes.large,
                scaffoldState = scaffoldState,
                sheetPeekHeight = 250.dp
            ) {
                //contenido de la pantalla
                ExecuteRoutineGlobal(
                    routine = uiState.routine,
                    modifier = Modifier.padding(it)
                )
            }
        }else if(uiState.message!=null){
            val dismissString = stringResource(id = R.string.snackbar_dismiss)
            val message = stringResource(id = uiState.message)
            // Muestra un snackbar
            LaunchedEffect(scaffoldState.snackbarHostState){
                val result = scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = dismissString,
                    duration = SnackbarDuration.Indefinite
                )
                when (result) {
                    SnackbarResult.Dismissed -> {
                        viewModel.dismissMessage()
                    }
                    SnackbarResult.ActionPerformed ->{
                        viewModel.dismissMessage()
                    }
                }
            }
        }
    }else{
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    executeRoutineNavigation: ExecuteRoutineNavigation
){

    TopNavigationBar(
        scrollBehavior = scrollBehavior,
        leftIcon = {
            IconButton(onClick = {
                executeRoutineNavigation.getPreviousScreen().invoke()
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
                text = title,
                style = MaterialTheme.typography.h2,
                color = FitiWhiteText
            )
        },
        rightIcon = null,
        secondRightIcon = null
    )
}

/*
@Preview(showBackground = true)
@Composable
fun ExecuteRoutineGlobalPreview() {
    TP3_HCITheme {
        ExecuteRoutine(routine = RoutineDetailUiState("Futbol",3,"Jose",3,120000,listOf("Hola","Como","estas", "buenas","tardes","Futbol","Scaloneta","Messi"), cycles))
    }
}

 */