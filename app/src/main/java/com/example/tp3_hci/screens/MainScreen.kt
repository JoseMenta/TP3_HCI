package com.example.tp3_hci.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.components.routine.*
import com.example.tp3_hci.ui.theme.FitiBlueText
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp3_hci.data.model.RoutineOverview
import com.example.tp3_hci.data.view_model.MainScreenViewModel
import com.example.tp3_hci.util.getViewModelFactory
import com.example.tp3_hci.utilities.*
import com.example.tp3_hci.utilities.TopAppBarState
import com.example.tp3_hci.utilities.navigation.MainScreenNavigation
import kotlin.math.min



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainScreenNavigation: MainScreenNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    mainScreenViewModel : MainScreenViewModel = viewModel(factory = getViewModelFactory()),
    scaffoldState: ScaffoldState
){
    val windowInfo = rememberWindowInfo()

    var createdRoutinesTextStyle : TextStyle by remember {
        mutableStateOf(TextStyle.Default)
    }
    var scrollBehavior : TopAppBarScrollBehavior? by remember {
        mutableStateOf(null)
    }

    // Para moviles
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact ||
            windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact){
        createdRoutinesTextStyle = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    }
    // Para tablets
    else {
        createdRoutinesTextStyle = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold)
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    }

    MainScreenContent(
        createdRoutinesTextStyle = createdRoutinesTextStyle,
        setTopAppBar = setTopAppBar,
        mainScreenNavigation = mainScreenNavigation,
        mainScreenViewModel = mainScreenViewModel,
        scrollBehavior = scrollBehavior!!,
        scaffoldState = scaffoldState,
        simplify = mainScreenViewModel.getSimplify()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    createdRoutinesTextStyle : TextStyle,
    setTopAppBar : ((TopAppBarType)->Unit),
    mainScreenNavigation : MainScreenNavigation,
    mainScreenViewModel : MainScreenViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    scaffoldState: ScaffoldState,
    simplify : Boolean
){
    var topAppBarState by remember {
        mutableStateOf(TopAppBarState.Regular as TopAppBarState)
    }
    //TODO: ver como hacer para que se haga solo la primera vez
    //Tambien se me ocurrio un counter, y que lo haga si es 0
    //if(!mainScreenViewModel.mainScreenUiState.isLoading && mainScreenViewModel.mainScreenUiState.message!=null) {
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
    //}
    val mainScreenUiState = mainScreenViewModel.mainScreenUiState

    RegularDisplay(
        content = {
            RoutineCardDisplay(
                modifier = Modifier.padding(horizontal = 20.dp).fillMaxHeight(),
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
                                },
                                simplify = simplify
                            )
                        }

                        Text(
                            text = stringResource(id = R.string.created_routined),
                            style = createdRoutinesTextStyle,
                            color = FitiBlueText,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        RoutineOrderDropDown(
                            modifier = Modifier.padding(bottom = 10.dp),
                            onOrderByChange = { orderByItem -> mainScreenViewModel.setOrderByItem(orderByItem) },
                            onOrderTypeChange = { orderTypeItem -> mainScreenViewModel.setOrderTypeItem(orderTypeItem) },
                            orderByItem = mainScreenUiState.orderBy,
                            orderTypeItem = mainScreenUiState.orderType
                        )
                    }
                },
                routineCardNavigation = mainScreenNavigation.getRoutineCardNavigation(),
                onFavoriteChange = {
                        routine -> mainScreenViewModel.toggleRoutineFavorite(routine)
                },
                simplify = simplify
            )
        },
        topAppBarState = topAppBarState,
        hasSearch = true,
        hasSwipeRefresh = true,
        isRefreshing = mainScreenUiState.isLoading,
        onRefreshSwipe = { mainScreenViewModel.reloadMainScreenContent() }
    )

    if(mainScreenUiState.hasError()){
        ErrorSnackBar(
            scaffoldState = scaffoldState,
            message = mainScreenUiState.message!!,
            onActionLabelClicked = { mainScreenViewModel.dismissMessage() }
        )
    }
}



@Composable
private fun LastRoutineDoneDisplay(
    lastRoutineDone : List<MutableState<RoutineOverview>>,
    mainScreenNavigation: MainScreenNavigation,
    simplify : Boolean,
    onFavoriteChange: (MutableState<RoutineOverview>)->Unit
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
            onFavoriteChange = onFavoriteChange,
            simplify  = simplify
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
                    onFavoriteChange = onFavoriteChange,
                    simplify = simplify
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