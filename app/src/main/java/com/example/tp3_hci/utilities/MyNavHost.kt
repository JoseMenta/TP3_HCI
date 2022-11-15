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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tp3_hci.screens.ExecuteRoutine
import com.example.tp3_hci.screens.LoginView
import com.example.tp3_hci.R
import com.example.tp3_hci.screens.RoutineDetail
import com.example.tp3_hci.components.navigation.BottomNavItem
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.RegularBottomNavItem
import com.example.tp3_hci.components.navigation.RegularBottomNavItem.Favorite.getBottomNavItems
import com.example.tp3_hci.screens.RatingView
import com.example.tp3_hci.screens.cycles
import com.example.tp3_hci.data.RoutineDetailUiState
import com.example.tp3_hci.screens.FavoritesScreen
import com.example.tp3_hci.screens.MainScreen
import com.example.tp3_hci.screens.Routines
import com.example.tp3_hci.screens.SearchResultsScreen
import com.example.tp3_hci.utilities.navigation.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = "Login"
) {
    val uri = "https://fiti.com"

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    var topAppBar by remember {
        mutableStateOf(TopAppBarType(topAppBar = null))
    }
    val changeTopAppBarType = { newTopAppBar : TopAppBarType ->
        topAppBar = newTopAppBar
    }

    var showBottomBar by rememberSaveable { mutableStateOf(false) }
    showBottomBar = (getBottomNavItems().find { bottomNavItem ->
        navBackStackEntry?.destination?.route == bottomNavItem.route
    } != null)

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
            composable("RoutineDetails/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
                deepLinks = listOf(navDeepLink { uriPattern = "$uri/RoutineDetails/{id}"
                    action = Intent.ACTION_VIEW})){

                //Todos estos filters despues se sacan y se pone el manejo de la API
                val aux = Routines.filter { routine -> routine.id == (it.arguments?.getInt("id") ?: 0) }.first()
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
                    routine = RoutineDetailUiState(aux.id, aux.name,3,"Jose",aux.score,120000,aux.tags!!, cycles),
                    srcImg = aux.imageUrl!!
                )
            }
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




