package com.example.tp3_hci.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp3_hci.R
import com.example.tp3_hci.components.routine.*
import com.example.tp3_hci.data.view_model.FavoritesScreenViewModel
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.util.getViewModelFactory
import com.example.tp3_hci.utilities.*
import com.example.tp3_hci.utilities.navigation.FavoritesNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoritesNavigation: FavoritesNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    favoritesScreenViewModel: FavoritesScreenViewModel = viewModel(factory = getViewModelFactory()),
    scaffoldState: ScaffoldState
){
    val windowInfo = rememberWindowInfo()

    var favoriteRoutinesTextStyle : TextStyle by remember {
        mutableStateOf(TextStyle.Default)
    }
    var scrollBehavior : TopAppBarScrollBehavior? by remember {
        mutableStateOf(null)
    }

    // Para moviles
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact ||
        windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact){
        favoriteRoutinesTextStyle = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    }
    // Para tablets
    else {
        favoriteRoutinesTextStyle = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold)
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    }

    FavoritesScreenContent(
        favoriteRoutinesTextStyle = favoriteRoutinesTextStyle,
        setTopAppBar = setTopAppBar,
        favoritesNavigation = favoritesNavigation,
        favoritesScreenViewModel = favoritesScreenViewModel,
        scrollBehavior = scrollBehavior!!,
        scaffoldState = scaffoldState,
        simplify = favoritesScreenViewModel.getSimplify(),
    )
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun FavoritesScreenTablet(
//    favoritesNavigation: FavoritesNavigation,
//    setTopAppBar : ((TopAppBarType)->Unit),
//    favoriteRoutines : List<RoutineCardUiState>? = null,
//){
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
//    var topAppBarState by remember {
//        mutableStateOf(TopAppBarState.Regular as TopAppBarState)
//    }
//    setTopAppBar(
//        TopAppBarType {
//            RegularTopAppBar(
//                scrollBehavior = scrollBehavior,
//                topAppBarState = topAppBarState,
//                onTopAppBarState = {
//                    topAppBarState = it
//                },
//                searchNavigation = favoritesNavigation.getSearchNavigation()
//            )
//        }
//    )
//
//    RegularTabletDisplay(
//        content = {
//            RoutineCardDisplay(
//                modifier = Modifier
//                    .padding(horizontal = 20.dp),
//                routines = favoriteRoutines,
//                header = {
//                    Column(
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.favorites),
//                            style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
//                            color = FitiBlueText,
//                            modifier = Modifier.padding(vertical = 10.dp)
//                        )
//                    }
//                },
//                routineCardNavigation = favoritesNavigation.getRoutineCardNavigation()
//            )
//        },
//        topAppBarState = topAppBarState
//    )
//}
//
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun FavoritesScreenMobile(
//    favoritesNavigation: FavoritesNavigation,
//    setTopAppBar : ((TopAppBarType)->Unit),
//    favoriteRoutines : List<RoutineCardUiState>? = null
//){
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
//    var topAppBarState by remember {
//        mutableStateOf(TopAppBarState.Regular as TopAppBarState)
//    }
//    setTopAppBar(
//        TopAppBarType {
//            RegularTopAppBar(
//                scrollBehavior = scrollBehavior,
//                topAppBarState = topAppBarState,
//                onTopAppBarState = {
//                    topAppBarState = it
//                },
//                searchNavigation = favoritesNavigation.getSearchNavigation()
//            )
//        }
//    )
//
//    RegularMobileDisplay(
//        content = {
//            RoutineCardDisplay(
//                modifier = Modifier
//                    .padding(horizontal = 20.dp),
//                routines = favoriteRoutines,
//                header = {
//                    Column(
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.favorites),
//                            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
//                            color = FitiBlueText,
//                            modifier = Modifier.padding(vertical = 10.dp)
//                        )
//                    }
//                },
//                routineCardNavigation = favoritesNavigation.getRoutineCardNavigation()
//            )
//        },
//        topAppBarState = topAppBarState
//    )
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreenContent(
    favoriteRoutinesTextStyle: TextStyle,
    setTopAppBar: (TopAppBarType) -> Unit,
    favoritesNavigation: FavoritesNavigation,
    favoritesScreenViewModel: FavoritesScreenViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    scaffoldState: ScaffoldState,
    simplify : Boolean
){
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
                searchNavigation = favoritesNavigation.getSearchNavigation()
            )
        }
    )


    val favoritesScreenUiState = favoritesScreenViewModel.favoritesScreenUiState

    RegularDisplay(
        content = {
            RoutineCardDisplay(
                modifier = Modifier
                    .padding(horizontal = 20.dp).fillMaxHeight(),
                routines = favoritesScreenUiState.favoriteRoutines,
                header = {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.height(IntrinsicSize.Max)
                    ) {
                        Text(
                            text = stringResource(id = R.string.favorites),
                            style = favoriteRoutinesTextStyle,
                            color = FitiBlueText,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        RoutineOrderDropDown(
                            modifier = Modifier.padding(bottom = 10.dp),
                            onOrderByChange = { orderByItem -> favoritesScreenViewModel.setOrderByItem(orderByItem) },
                            onOrderTypeChange = { orderTypeItem -> favoritesScreenViewModel.setOrderTypeItem(orderTypeItem) },
                            orderByItem = favoritesScreenUiState.orderBy,
                            orderTypeItem = favoritesScreenUiState.orderType
                        )
                    }
                },
                routineCardNavigation = favoritesNavigation.getRoutineCardNavigation(),
                onFavoriteChange = {
                    routine -> favoritesScreenViewModel.toggleRoutineFavorite(routine)
                },
                simplify = simplify
            )
        },
        topAppBarState = topAppBarState,
        hasSearch = true,
        hasSwipeRefresh = true,
        onRefreshSwipe = {
            favoritesScreenViewModel.reloadFavoritesScreenContent()
        },
        isRefreshing = favoritesScreenUiState.isLoading
    )

    if(favoritesScreenUiState.hasError()){
        ErrorSnackBar(
            scaffoldState = scaffoldState,
            message = favoritesScreenUiState.message!!,
            onActionLabelClicked = {
                favoritesScreenViewModel.dismissMessage()
            }
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
private fun FavoritesScreenPreview(){
    TP3_HCITheme {
        FavoritesScreen(
            favoriteRoutines = listOf(
                RoutineCardUiState("Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
                RoutineCardUiState("Yoga", true, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
                RoutineCardUiState("Abdominales", true, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
                RoutineCardUiState("Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
                RoutineCardUiState("Atletismo", true, 1, listOf("Piernas", "Exigente", "Cinta", "Bicicleta"), "https://concepto.de/wp-content/uploads/2015/03/atletismo-e1550017721661.jpg")
            ),
            onNavigateToRoutineDetailScreen = {},
            setTopAppBar = {}
        )
    }
}
 */