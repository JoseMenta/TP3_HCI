package com.example.tp3_hci.utilities

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
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
import com.example.tp3_hci.components.navigation.*
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.NavigationDrawer
import com.example.tp3_hci.components.navigation.RegularBottomNavItem.Favorite.getBottomNavItems

import com.example.tp3_hci.screens.*
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiWhiteText
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
    var restartSelectedNavigation = false
    if((navBackStackEntry?.destination?.route) == "MainScreen"){
        restartSelectedNavigation = true
    }
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
        showBottomBar = false
        if( (navBackStackEntry?.destination?.route) == "Login" ){

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
        else{
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
                },
                actions = {
                    navBarItem -> navController.navigate(navBarItem.route)
                },
                restartSelectedNavigation = restartSelectedNavigation
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun scaf(
    uri : String,
    changeTopAppBarType : (TopAppBarType) -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    showBottomBar: Boolean,
    topAppBar :  TopAppBarType,
    navController : NavHostController,
    startDestination : String
){
    val scaffoldState = rememberScaffoldState()
    var prevRoute = navController.previousBackStackEntry
    val navBackStackEntry by navController.currentBackStackEntryAsState()

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
                    route = navBackStackEntry?.destination?.route,
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
        },
        scaffoldState = scaffoldState,
        snackbarHost = { snackbarHostState -> SnackbarHost(
            hostState = snackbarHostState,
            snackbar = {
                AppSnackBar(it)
            }
        ) }
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
                                search, searchingRoutineName, rating, difficulty, category -> navController.navigate("SearchResults/${search}/${searchingRoutineName}?rating=${rating}?difficulty=${difficulty}?category=${category}") {
                            launchSingleTop = true
                        }
                        }
                    ),
                    setTopAppBar = changeTopAppBarType,
                    scaffoldState = scaffoldState
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
                                search, searchingRoutineName, rating, difficulty, category -> navController.navigate("SearchResults/${search}/${searchingRoutineName}?rating=${rating}?difficulty=${difficulty}?category=${category}") {
                            launchSingleTop = true
                        }
                        }
                    ),
                    setTopAppBar = changeTopAppBarType,
                    scaffoldState = scaffoldState
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
                    setTopAppBar = changeTopAppBarType,
                    scaffoldState = scaffoldState
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
                            if(prevRoute == null || prevRoute.destination.route == "Routine/{id}") {
                                navController.navigate("MainScreen") {
                                    launchSingleTop = true
                                }
                            }else{
                                navController.navigateUp()
                            }
                        },
                        executeRoutineScreen = {
                                routine -> navController.navigate("MakeRoutine/${routine}") {
                            launchSingleTop = true
                        }
                        }
                    ),
                    setTopAppBar = changeTopAppBarType,
                    routineId = it.arguments?.getInt("id") ?: -1,
                    scaffoldState = scaffoldState
                )
            }
            composable("MakeRoutine/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })){
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
                    routineId = it.arguments?.getInt("id")!!
                )
            }
            composable("RatingRoutine/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })){


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
                    routineId = it.arguments?.getInt("id")!!
                )
            }
            composable("SearchResults/{search}/{searchingRoutineName}?rating={rating}?difficulty={difficulty}?category={category}",
                arguments = listOf(
                    navArgument("search") { type = NavType.StringType },
                    navArgument("searchingRoutineName") { type = NavType.BoolType },
                    navArgument("rating") {
                        type = NavType.IntType
                    },
                    navArgument("difficulty") {
                        type = NavType.IntType
                    },
                    navArgument("category") {
                        nullable = true
                        type = NavType.StringType
                    }
                )
            ){
                SearchResultsScreen(
                    stringSearched = it.arguments?.getString("search")!!,
                    searchingRoutineName = it.arguments?.getBoolean("searchingRoutineName")!!,
                    ratingSearched = if(it.arguments?.getInt("rating") == -1) null else it.arguments?.getInt("rating"),
                    difficultySearched = if(it.arguments?.getInt("difficulty") == -1) null else it.arguments?.getInt("difficulty"),
                    categorySearched = it.arguments?.getString("category"),
                    setTopAppBar = changeTopAppBarType,
                    scaffoldState = scaffoldState,
                    searchResultsNavigation = SearchResultsNavigation(
                        previousScreen = {
                            navController.navigateUp()
                        },
                        searchNavigation = SearchNavigation {
                                search, searchingRoutineName, rating, difficulty, category -> navController.navigate("SearchResults/${search}/${searchingRoutineName}?rating=${rating}?difficulty=${difficulty}?category=${category}")
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


@Composable
private fun AppSnackBar(
    content: SnackbarData
){
    Snackbar(
        shape = MaterialTheme.shapes.small,
        backgroundColor = FitiBlue,
        contentColor = FitiWhiteText,
        snackbarData = content,
        actionColor = FitiWhiteText
    )
}



