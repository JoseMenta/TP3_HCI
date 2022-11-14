package com.example.tp3_hci.utilities

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tp3_hci.ExecuteRoutine
import com.example.tp3_hci.LoginView
import com.example.tp3_hci.R
import com.example.tp3_hci.RoutineDetail
import com.example.tp3_hci.components.navigation.BottomNavItem
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.review.ratingView
import com.example.tp3_hci.cycles
import com.example.tp3_hci.data.RoutineDetailUiState
import com.example.tp3_hci.screens.FavoritesScreen
import com.example.tp3_hci.screens.MainScreen
import com.example.tp3_hci.screens.Routines
import com.example.tp3_hci.screens.SearchResultsScreen
import com.example.tp3_hci.ui.theme.FitiWhiteText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = "login"
) {
    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "login" -> false // on this screen bottom bar should be hidden
        "MakeRuotine/{name}" -> false
        "RatingRoutine/{name}" -> false
        else -> true // in all other cases show bottom bar
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val bottomNavItems : List<BottomNavItem> = listOf(
        BottomNavItem(R.string.bottom_nav_favorites, { navController.navigate("Favorites") }, Icons.Filled.Favorite),
        BottomNavItem(R.string.bottom_nav_home, { navController.navigate("MainScreen") }, Icons.Filled.Home),
        BottomNavItem(R.string.bottom_nav_profile, { navController.navigate("Favorites") }, Icons.Filled.Person)
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            if (showBottomBar) BottomNavigationBar(items = bottomNavItems)
        },
        topBar = {
            if (showBottomBar) TopNavigationBar(
                scrollBehavior = scrollBehavior,
                rightIcon = {
                    IconButton(onClick = { navController.navigate("SearchResults")  }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.search),
                            tint = FitiWhiteText,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                centerComponent = {
                    Text(
                        text = stringResource(id = R.string.fiti),
                        style = MaterialTheme.typography.h3.copy(fontSize = 22.sp),
                        color = FitiWhiteText
                    )
                },
                defaulNav = { navController.navigate("MainScreen"){
                    popUpTo("MainScreen")
                } }
            )
        }
    ){innerPadding ->
        NavHost(
            navController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding)) {
            composable("login") {
                LoginView(
                    onNavigateToMainScreen = {navController.navigate("MainScreen")}
                )
            }
            composable("MainScreen"){
                MainScreen(
                    onNavigateToRutineDetailScreen = {name -> navController.navigate("RutineDetails/$name")},
                    onNavigateToResetHomeScreen = {navController.navigate("MainScreen"){
                        popUpTo("MainScreen")
                    } },
                    lastRoutineDone =Routines,
                    /*lastRoutineDone = listOf(
                        RoutineCardUiState("Futbol", true, 4, listOf("Abdominales", "Piernas", "Gemelos", "Cabeza", "Pelota"), "https://phantom-marca.unidadeditorial.es/4a48d118c4427fc01575ac7e16d4b4a0/crop/0x70/1022x644/resize/1320/f/jpg/assets/multimedia/imagenes/2021/07/11/16259717481572.jpg")
                    ),*/
                    createdRoutines = Routines
                )
            }
            composable("Favorites"){
                FavoritesScreen(
                    onNavigateToRutineDetailScreen = {name -> navController.navigate("RutineDetails/$name")},
                    onNavigateToResetHomeScreen = {navController.navigate("MainScreen"){
                        popUpTo("MainScreen")
                    } },
                    favoriteRoutines = Routines
                )
            }
            composable("RutineDetails/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })){

                //Todos estos filters despues se sacan y se pone el manejo de la API
                val aux = Routines.filter { routine -> routine.name == (it.arguments?.getString("name") ?: "") }.first()
                RoutineDetail(
                    onNavigateToMakeRuotineScreen = {navController.navigate("MakeRuotine/${aux.name}")},
                    routine = RoutineDetailUiState(aux.name,3,"Jose",aux.score,120000,aux.tags!!, cycles),
                    srcImg = aux.imageUrl!!)
            }
            composable("MakeRuotine/{name}",
                    arguments = listOf(navArgument("name") { type = NavType.StringType })){

                val aux = Routines.filter { routine -> routine.name == it.arguments?.getString("name")!! }.first()
                ExecuteRoutine(
                    onNavigateToRatingRoutineScreen = {navController.navigate("RatingRoutine/${aux.name}")},
                    routine = RoutineDetailUiState(aux.name,3,"Jose",aux.score,120000,aux.tags!!, cycles),
                )
            }
            composable("RatingRoutine/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })){

                val aux = Routines.filter { routine -> routine.name == it.arguments?.getString("name")!! }.first()
                ratingView(
                    onNavigateToHomeScreen = {navController.navigate("MainScreen")},
                    aux
                )
            }
            composable("SearchResults"){

                SearchResultsScreen(
                    stringSearched= "",
                    routinesFound = Routines,
                    onNavigateToRutineDetailScreen = {name -> navController.navigate("RutineDetails/$name")},
                )
            }
        }
    }
}




