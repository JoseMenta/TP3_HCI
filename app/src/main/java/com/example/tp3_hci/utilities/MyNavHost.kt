package com.example.tp3_hci.utilities

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.BottomNavItem
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.NavigationDrawer
import com.example.tp3_hci.components.navigation.RegularBottomNavItem
import com.example.tp3_hci.components.navigation.RegularBottomNavItem.Favorite.getBottomNavItems
import com.example.tp3_hci.data.ui_state.RoutineDetailUiState
import com.example.tp3_hci.screens.*
import com.example.tp3_hci.utilities.navigation.*

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = "Login"
) {
    val uri = "https://fiti.com"

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var topAppBar by remember {
        mutableStateOf(TopAppBarType(topAppBar = null))
    }
    val changeTopAppBarType = { newTopAppBar: TopAppBarType ->
        topAppBar = newTopAppBar
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var showBottomBar by rememberSaveable { mutableStateOf(false) }
    showBottomBar = (getBottomNavItems().find { bottomNavItem ->
        navBackStackEntry?.destination?.route == bottomNavItem.route
    } != null)

    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact ||
        windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact
    ) {
        scaf(
            uri = uri,
            changeTopAppBarType = changeTopAppBarType,
            modifier = modifier,
            scrollBehavior = scrollBehavior,
            showBottomBar = showBottomBar,
            topAppBar = topAppBar,
            navController = navController,
            startDestination = startDestination
        )
    } else {
        NavigationDrawer(
            content = {
                scaf(
                    uri = uri,
                    changeTopAppBarType = changeTopAppBarType,
                    modifier = modifier,
                    scrollBehavior = scrollBehavior,
                    showBottomBar = showBottomBar,
                    topAppBar = topAppBar,
                    navController = navController,
                    startDestination = startDestination
                )
            }
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun scaf(
    uri : String,
    changeTopAppBarType : (TopAppBarType) -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    showBottomBar: Boolean,
    topAppBar :  TopAppBarType,
    navController : NavHostController,
    startDestination : String
){
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = expandVertically(
                    expandFrom = Alignment.Bottom
                ) + fadeIn(),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Bottom
                ) + fadeOut()
            ) {
                BottomNavigationBar(
                    items = getBottomNavItems(),
                    bottomBarNavigation = BottomBarNavigation {
                            route -> navController.navigate(route) {
                        launchSingleTop = true
                    }
                    }
                )
            }
        },
        topBar = {
            AnimatedVisibility(
                visible = (topAppBar.getTopAppBar() != null),
                enter = expandVertically(
                    expandFrom = Alignment.Bottom
                ) + fadeIn(),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Bottom
                ) + fadeOut()
            ) {
                if(topAppBar.getTopAppBar() != null){
                    topAppBar.getTopAppBar()!!.invoke()
                }
            }
        }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding)
        ) {
            composable("Login") {
                LoginView(
                    setTopAppBar = changeTopAppBarType,
                    loginNavigation = LoginNavigation {
                        navController.navigate("MainScreen") {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable("MainScreen"){
                MainScreen(
                    mainScreenNavigation = MainScreenNavigation(
                        routineCardNavigation = RoutineCardNavigation {
                                routine -> navController.navigate("RoutineDetails/${routine}") {
                            launchSingleTop = true
                        }
                        },
                        searchNavigation = SearchNavigation {
                                search -> navController.navigate("SearchResults/${search}") {
                            launchSingleTop = true
                        }
                        }
                    ),
                    setTopAppBar = changeTopAppBarType,
                    lastRoutineDone = Routines,
                    createdRoutines = Routines
                )
            }
            composable("Favorites"){
                FavoritesScreen(
                    favoritesNavigation = FavoritesNavigation(
                        routineCardNavigation = RoutineCardNavigation {
                                routine -> navController.navigate("RoutineDetails/${routine}") {
                            launchSingleTop = true
                        }
                        },
                        searchNavigation = SearchNavigation {
                                search -> navController.navigate("SearchResults/${search}") {
                            launchSingleTop = true
                        }
                        }
                    ),
                    setTopAppBar = changeTopAppBarType,
                    favoriteRoutines = Routines
                )
            }
            composable("Profile"){
                ProfileScreen(
                    profileNavigation = profileNavigation(
                        LoginNavigation = {
                            navController.navigate("Login") {
                                launchSingleTop = true
                            }
                        },
                        previousScreen = {
                            navController.navigateUp()
                        }
                    ),
                    setTopAppBar = changeTopAppBarType
                )
            }
            composable("Routine/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
                deepLinks = listOf(navDeepLink { uriPattern = "$uri/Routine/{id}"
                    action = Intent.ACTION_VIEW})){

                LoginView(
                    setTopAppBar = changeTopAppBarType,
                    loginNavigation = LoginNavigation {
                        navController.navigate("RoutineDetails/${it.arguments?.getString("id")}") {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable("RoutineDetails/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })){

                //Todos estos filters despues se sacan y se pone el manejo de la API
                RoutineDetail(
                    routineDetailNavigation = RoutineDetailNavigation(
                        previousScreen = {
                            navController.navigateUp()
                        },
                        executeRoutineScreen = {
                                routine -> navController.navigate("MakeRoutine/${routine}") {
                            launchSingleTop = true
                        }
                        }
                    ),
                    setTopAppBar = changeTopAppBarType,
                    routineId = it.arguments?.getInt("id") ?: -1
                )
            }
            /*
            composable("MakeRoutine/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })){

                val aux = Routines.filter { routine -> routine.id == it.arguments?.getInt("id")!! }.first()
                ExecuteRoutine(
                    executeRoutineNavigation = ExecuteRoutineNavigation(
                        previousScreen = {
                            navController.navigateUp()
                        },
                        rateRoutineScreen = {
                                routine -> navController.navigate("RatingRoutine/${routine}") {
                            launchSingleTop = true
                        }
                        }
                    ),
                    setTopAppBar = changeTopAppBarType,
                    routine = RoutineDetailUiState(aux.id, aux.name,3,"Jose",aux.score,120000,aux.tags!!, cycles),
                )
            }
            */
            composable("RatingRoutine/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })){

                val aux = Routines.filter { routine -> routine.id == it.arguments?.getInt("id")!! }.first()
                RatingView(
                    viewRatingNavigation = ViewRatingNavigation(
                        homeScreen = {
                            navController.navigate("MainScreen") {
                                launchSingleTop = true
                            }
                        },
                        previousScreen = {
                            navController.navigateUp()
                        }
                    ),
                    setTopAppBar = changeTopAppBarType,
                    routine = aux
                )
            }
            composable("SearchResults/{search}",
                arguments = listOf(
                    navArgument("search") { type = NavType.StringType }
                )
            ){

                SearchResultsScreen(
                    stringSearched = it.arguments?.getString("search")!! ,
                    routinesFound = Routines,
                    setTopAppBar = changeTopAppBarType,
                    searchResultsNavigation = SearchResultsNavigation(
                        previousScreen = {
                            navController.navigateUp()
                        },
                        searchNavigation = SearchNavigation {
                                search -> navController.navigate("SearchResults/${search}")
                        },
                        routineCardNavigation = RoutineCardNavigation {
                                routine -> navController.navigate("RoutineDetails/${routine}") {
                            launchSingleTop = true
                        }
                        }
                    )
                )
            }
        }
    }
}



