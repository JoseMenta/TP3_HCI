package com.example.tp3_hci.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.components.routine.*
import com.example.tp3_hci.ui.theme.FitiBlueText
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp3_hci.data.model.RoutineOverview
import com.example.tp3_hci.data.ui_state.RoutineCardUiState
import com.example.tp3_hci.data.view_model.MainScreenViewModel
import com.example.tp3_hci.util.ViewModelFactory
import com.example.tp3_hci.util.getViewModelFactory
import com.example.tp3_hci.utilities.*
import com.example.tp3_hci.utilities.navigation.MainScreenNavigation
import kotlin.math.min

val Routines = listOf(
    RoutineCardUiState(1,"Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
    RoutineCardUiState(2,"Yoga", true, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
    RoutineCardUiState(3,"Abdominales", true, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
    RoutineCardUiState(4,"Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
    RoutineCardUiState(5,"Atletismo", true, 1, listOf("Piernas", "Exigente", "Cinta", "Bicicleta"), "https://concepto.de/wp-content/uploads/2015/03/atletismo-e1550017721661.jpg")
)


@Composable
fun MainScreen(
    mainScreenNavigation: MainScreenNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    mainScreenViewModel : MainScreenViewModel = viewModel(factory = getViewModelFactory()),
    scaffoldState: ScaffoldState
){
    val windowInfo = rememberWindowInfo()

    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact ||
            windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact){
        MainScreenMobile(
            mainScreenNavigation = mainScreenNavigation,
            mainScreenViewModel = mainScreenViewModel,
            setTopAppBar = setTopAppBar,
            scaffoldState = scaffoldState
        )
    } else {
        MainScreenTablet(
            mainScreenNavigation = mainScreenNavigation,
            mainScreenViewModel = mainScreenViewModel,
            setTopAppBar = setTopAppBar,
            scaffoldState = scaffoldState
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenTablet(
    mainScreenNavigation: MainScreenNavigation,
    mainScreenViewModel : MainScreenViewModel,
    setTopAppBar : ((TopAppBarType)->Unit),
    scaffoldState: ScaffoldState
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var topAppBarState by remember {
        mutableStateOf(TopAppBarState.Regular as TopAppBarState)
    }
    setTopAppBar(
        TopAppBarType {
            RegularTopAppBar(
                scrollBehavior = scrollBehavior,
                topAppBarState = topAppBarState,
                onTopAppBarState = {
                    topAppBarState = it
                },
                searchNavigation = mainScreenNavigation.getSearchNavigation()
            )
        }
    )

    val mainScreenUiState = mainScreenViewModel.mainScreenUiState
    if(!mainScreenUiState.isFetched){
        if(mainScreenUiState.createdRoutines == null){
            mainScreenViewModel.getCreatedRoutines()
        }
        if(mainScreenUiState.lastRoutinesExecuted == null){
            mainScreenViewModel.getLastExecutionRoutines()
        }
    }

    if(!mainScreenUiState.isLoading){
        RegularMobileDisplay(
            content = {
                RoutineCardDisplay(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    routines = mainScreenUiState.createdRoutines,
                    header = {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (mainScreenUiState.lastRoutinesExecuted != null && mainScreenUiState.lastRoutinesExecuted.isNotEmpty()) {
                                LastRoutineDoneDisplay(
                                    lastRoutineDone = mainScreenUiState.lastRoutinesExecuted,
                                    mainScreenNavigation = mainScreenNavigation,
                                    onFavoriteChange = {
                                            routine -> mainScreenViewModel.toggleRoutineFavorite(routine)
                                    }
                                )
                            }

                            Text(
                                text = stringResource(id = R.string.created_routined),
                                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
                                color = FitiBlueText,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )

                            RoutineOrderDropDown(
                                modifier = Modifier.padding(bottom = 10.dp),
                                onOrderByChange = { orderByItem -> mainScreenViewModel.setOrderByItem(orderByItem) },
                                onOrderTypeChange = { orderTypeItem -> mainScreenViewModel.setOrderTypeItem(orderTypeItem) }
                            )
                        }
                    },
                    routineCardNavigation = mainScreenNavigation.getRoutineCardNavigation(),
                    onFavoriteChange = {
                            routine -> mainScreenViewModel.toggleRoutineFavorite(routine)
                    }
                )
            },
            topAppBarState = topAppBarState
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
        }
    }

    // Muestra un snackbar en caso de error
    if(mainScreenUiState.hasError()){
        LaunchedEffect(scaffoldState.snackbarHostState){
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = mainScreenUiState.message!!,
                actionLabel = "Cerrar"
            )
            when (result) {
                SnackbarResult.Dismissed -> mainScreenViewModel.dismissMessage()
                SnackbarResult.ActionPerformed -> mainScreenViewModel.dismissMessage()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenMobile(
    mainScreenNavigation: MainScreenNavigation,
    mainScreenViewModel : MainScreenViewModel,
    setTopAppBar : ((TopAppBarType)->Unit),
    scaffoldState: ScaffoldState
){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var topAppBarState by remember {
        mutableStateOf(TopAppBarState.Regular as TopAppBarState)
    }
    setTopAppBar(
        TopAppBarType {
            RegularTopAppBar(
                scrollBehavior = scrollBehavior,
                topAppBarState = topAppBarState,
                onTopAppBarState = {
                    topAppBarState = it
                },
                searchNavigation = mainScreenNavigation.getSearchNavigation()
            )
        }
    )

    val mainScreenUiState = mainScreenViewModel.mainScreenUiState
    if(!mainScreenUiState.isFetched){
        if(mainScreenUiState.createdRoutines == null){
            mainScreenViewModel.getCreatedRoutines()
        }
        if(mainScreenUiState.lastRoutinesExecuted == null){
            mainScreenViewModel.getLastExecutionRoutines()
        }
    }

    if(!mainScreenUiState.isLoading){
        RegularMobileDisplay(
            content = {
                RoutineCardDisplay(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    routines = mainScreenUiState.createdRoutines,
                    header = {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (mainScreenUiState.lastRoutinesExecuted != null && mainScreenUiState.lastRoutinesExecuted.isNotEmpty()) {
                                LastRoutineDoneDisplay(
                                    lastRoutineDone = mainScreenUiState.lastRoutinesExecuted,
                                    mainScreenNavigation = mainScreenNavigation,
                                    onFavoriteChange = {
                                        routine -> mainScreenViewModel.toggleRoutineFavorite(routine)
                                    }
                                )
                            }

                            Text(
                                text = stringResource(id = R.string.created_routined),
                                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
                                color = FitiBlueText,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )

                            RoutineOrderDropDown(
                                modifier = Modifier.padding(bottom = 10.dp),
                                onOrderByChange = { orderByItem -> mainScreenViewModel.setOrderByItem(orderByItem) },
                                onOrderTypeChange = { orderTypeItem -> mainScreenViewModel.setOrderTypeItem(orderTypeItem) }
                            )
                        }
                    },
                    routineCardNavigation = mainScreenNavigation.getRoutineCardNavigation(),
                    onFavoriteChange = {
                        routine -> mainScreenViewModel.toggleRoutineFavorite(routine)
                    }
                )
            },
            topAppBarState = topAppBarState
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
        }
    }


    // Muestra un snackbar en caso de error
    if(mainScreenUiState.hasError()){
        LaunchedEffect(scaffoldState.snackbarHostState){
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = mainScreenUiState.message!!,
                actionLabel = "Cerrar"
            )
            when (result) {
                SnackbarResult.Dismissed -> mainScreenViewModel.dismissMessage()
                SnackbarResult.ActionPerformed -> mainScreenViewModel.dismissMessage()
            }
        }
    }
}





@Composable
private fun LastRoutineDoneDisplay(
    lastRoutineDone : List<RoutineOverview>,
    mainScreenNavigation: MainScreenNavigation,
    onFavoriteChange: (RoutineOverview)->Unit
){
    val windowInfo = rememberWindowInfo()

    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
        Text(
            text = stringResource(id = R.string.last_routine_done),
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            color = FitiBlueText,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        RoutineCard(
            routine = lastRoutineDone[0],
            modifier = Modifier.padding(bottom = 20.dp),
            routineCardNavigation = mainScreenNavigation.getRoutineCardNavigation(),
            onFavoriteChange = onFavoriteChange
        )
    } else {
        Text(
            text = stringResource(id = R.string.last_routines_done),
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
            color = FitiBlueText,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            for(i in 1..min(lastRoutineDone.size, getItemsInRow(windowInfo = windowInfo))){
                RoutineCard(
                    routine = lastRoutineDone[i-1],
                    modifier = Modifier
                        .weight(1f),
                    routineCardNavigation = mainScreenNavigation.getRoutineCardNavigation(),
                    onFavoriteChange = onFavoriteChange
                )
            }
        }
    }
}


@Composable
private fun getItemsInRow(
    windowInfo: WindowInfo
): Int {
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded &&
        windowInfo.screenHeightInfo !is WindowInfo.WindowType.Expanded){
        return 3
    }
    return 2
}


/*
@Preview(showBackground = true)
@Composable
private fun MainScreenPreview(){
    TP3_HCITheme {
        MainScreen(
            lastRoutineDone = listOf(
                RoutineCardUiState("Futbol", true, 4, listOf("Abdominales", "Piernas", "Gemelos", "Cabeza", "Pelota"), "https://phantom-marca.unidadeditorial.es/4a48d118c4427fc01575ac7e16d4b4a0/crop/0x70/1022x644/resize/1320/f/jpg/assets/multimedia/imagenes/2021/07/11/16259717481572.jpg"),
                RoutineCardUiState("Prensa", false, 1, null, "https://e00-marca.uecdn.es/assets/multimedia/imagenes/2022/04/09/16495114782056.jpg"),
                RoutineCardUiState("Estiramientos", true, 5, listOf("Relajante", "Espalda", "Musculos"), "https://www.realsimple.com/thmb/-Sm0TMzmjqHeAE-cNl-eDt-B0rY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/stretching-exercises-workout-easy-basic-2000-6aec559bf2c54cb99b035bc338992a13.jpg"),
            ),
            createdRoutines = listOf(
                    RoutineCardUiState("Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
                    RoutineCardUiState("Yoga", false, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
                    RoutineCardUiState("Abdominales", false, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
                    RoutineCardUiState("Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
                    RoutineCardUiState("null", false, 0, listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")),
            ),
            onNavigateToRoutineDetailScreen = {},
            setTopAppBar = {}
        )
    }
}
 */