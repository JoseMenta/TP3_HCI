package com.example.tp3_hci.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.tp3_hci.components.routine.RoutineCardDisplay
import com.example.tp3_hci.components.routine.RoutineOrderDropDown
import com.example.tp3_hci.data.ui_state.SearchResultsParameters
import com.example.tp3_hci.data.view_model.OnSearchViewModel
import com.example.tp3_hci.data.view_model.SearchResultsViewModel
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.util.getViewModelFactory
import com.example.tp3_hci.utilities.*
import com.example.tp3_hci.utilities.navigation.SearchResultsNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsScreen(
    searchResultsNavigation: SearchResultsNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    searchResultsViewModel: SearchResultsViewModel = viewModel(factory = getViewModelFactory()),
    onSearchViewModel: OnSearchViewModel = viewModel(factory = getViewModelFactory()),
    scaffoldState: ScaffoldState,
    stringSearched: String = "",
    searchingRoutineName: Boolean,
    ratingSearched: Int?,
    difficultySearched : Int?,
    categorySearched : String?,

) {
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

    SearchResultsContent(
        setTopAppBar = setTopAppBar,
        searchResultsNavigation = searchResultsNavigation,
        searchResultsViewModel = searchResultsViewModel,
        onSearchViewModel = onSearchViewModel,
        scrollBehavior = scrollBehavior!!,
        scaffoldState = scaffoldState,
        searchParameters = SearchResultsParameters(
            text = stringSearched,
            searchingRoutineName = searchingRoutineName,
            rating = ratingSearched,
            difficulty = difficultySearched,
            category = categorySearched
        )
    )

}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun SearchResultsScreenTablet(
//    searchResultsNavigation: SearchResultsNavigation,
//    stringSearched: String = "",
//    routinesFound : List<RoutineCardUiState>? = null,
//    setTopAppBar : ((TopAppBarType)->Unit),
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
//                leftIcon = {
//                    IconButton(onClick = {
//                        searchResultsNavigation.getPreviousScreen().invoke()
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = stringResource(id = R.string.search),
//                            tint = FitiWhiteText,
//                            modifier = Modifier.size(30.dp)
//                        )
//                    }
//                },
//                searchNavigation = searchResultsNavigation.getSearchNavigation()
//            )
//        }
//    )
//
//    RegularTabletDisplay(
//        content = {
//            RoutineCardDisplay(
//                modifier = Modifier
//                    .padding(horizontal = 20.dp),
//                routines = routinesFound,
//                header = {
//                    Column(
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.results_title, stringSearched),
//                            style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
//                            color = FitiBlueText,
//                            modifier = Modifier.padding(vertical = 10.dp)
//                        )
//                    }
//                },
//                routineCardNavigation = searchResultsNavigation.getRoutineCardNavigation()
//            )
//        },
//        topAppBarState = topAppBarState
//    )
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun SearchResultsScreenMobile(
//    searchResultsNavigation: SearchResultsNavigation,
//    stringSearched: String = "",
//    routinesFound : List<RoutineCardUiState>? = null,
//    setTopAppBar : ((TopAppBarType)->Unit),
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
//                leftIcon = {
//                    IconButton(onClick = {
//                        searchResultsNavigation.getPreviousScreen().invoke()
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = stringResource(id = R.string.search),
//                            tint = FitiWhiteText,
//                            modifier = Modifier.size(30.dp)
//                        )
//                    }
//                },
//                searchNavigation = searchResultsNavigation.getSearchNavigation()
//            )
//        }
//    )
//
//    RegularMobileDisplay(
//        content = {
//            RoutineCardDisplay(
//                modifier = Modifier
//                    .padding(horizontal = 20.dp),
//                routines = routinesFound,
//                header = {
//                    Column(
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.results_title, stringSearched),
//                            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
//                            color = FitiBlueText,
//                            modifier = Modifier.padding(vertical = 10.dp)
//                        )
//                    }
//                },
//                routineCardNavigation = searchResultsNavigation.getRoutineCardNavigation()
//            )
//        },
//        topAppBarState = topAppBarState
//    )
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchResultsContent(
    setTopAppBar: ((TopAppBarType) -> Unit),
    searchResultsNavigation : SearchResultsNavigation,
    searchResultsViewModel: SearchResultsViewModel,
    onSearchViewModel: OnSearchViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    scaffoldState: ScaffoldState,
    searchParameters : SearchResultsParameters
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
                leftIcon = {
                    IconButton(onClick = {
                        searchResultsNavigation.getPreviousScreen().invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.search),
                            tint = FitiWhiteText,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                searchNavigation = searchResultsNavigation.getSearchNavigation(),
                onSearchViewModel = onSearchViewModel,
                onErrorSearch = { searchResultsViewModel.onErrorSearch() }
            )
        }
    )

    val searchResultsScreenUiState = searchResultsViewModel.searchResultsScreenUiState

    RegularDisplay(
        onSearchViewModel = onSearchViewModel,
        content = {
            if(searchResultsScreenUiState.isFetched){
                RoutineCardDisplay(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    routines = searchResultsScreenUiState.results,
                    header = {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.results_title, searchResultsScreenUiState.searchParameters.getText()),
                                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
                                color = FitiBlueText,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )

                            RoutineOrderDropDown(
                                orderByItem = searchResultsScreenUiState.orderBy,
                                orderTypeItem = searchResultsScreenUiState.orderType,
                                onOrderByChange = {
                                        orderByItem -> searchResultsViewModel.setOrderByItem(orderByItem)
                                },
                                onOrderTypeChange = {
                                        orderTypeItem -> searchResultsViewModel.setOrderTypeItem(orderTypeItem)
                                },
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                    },
                    routineCardNavigation = searchResultsNavigation.getRoutineCardNavigation(),
                    onFavoriteChange = {
                            routine -> searchResultsViewModel.toggleRoutineFavorite(routine)
                    },
                    simplify = searchResultsViewModel.getSimplify()
                )
            } else {
                if(!searchResultsScreenUiState.isLoading){
                    searchResultsViewModel.getRoutinesFromSearchResult(searchParameters)
                }
            }

        },
        topAppBarState = topAppBarState,
        hasSearch = true,
        hasSwipeRefresh = true,
        isRefreshing = searchResultsScreenUiState.isLoading,
        onRefreshSwipe = {
            searchResultsViewModel.reloadSearchResultsScreenContent()
        }
    )

    if(searchResultsScreenUiState.hasError()){
        ErrorSnackBar(
            scaffoldState = scaffoldState,
            message = stringResource(id = searchResultsScreenUiState.message ?: R.string.unexpected_error),
            onActionLabelClicked = {
                searchResultsViewModel.dismissMessage()
            }
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
private fun SearchResultsScreenPreview(){
    TP3_HCITheme {
        SearchResultsScreen(
            stringSearched = "Fu",
            routinesFound = listOf(
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
