package com.example.tp3_hci.utilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tp3_hci.LoginView
import com.example.tp3_hci.RoutineDetail
import com.example.tp3_hci.cycles
import com.example.tp3_hci.data.RoutineCardUiState
import com.example.tp3_hci.data.RoutineDetailUiState
import com.example.tp3_hci.screens.FavoritesScreen
import com.example.tp3_hci.screens.MainScreen

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login"
) {
    val uri = "http://www.fiti.com"
    val secureUri = "https://www.fiti.com"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier) {
        composable("login") {
            LoginView(
                onNavigateToMainScreen = {navController.navigate("MainScreen")}
            )
        }
        composable("MainScreen"){
            MainScreen(
                onNavigateToFavoritesScreen = {navController.navigate("Favorites")},
                onNavigateToHomeScreen = {navController.navigate("MainScreen")},
                onNavigateToProfileScreen = {navController.navigate("Profile")},
                onNavigateToRutineDetailScreen = {navController.navigate("RutineDetails")},
                onNavigateToResetHomeScreen = {navController.navigate("MainScreen"){
                    popUpTo("MainScreen")
                } },
                lastRoutineDone = listOf(
                    RoutineCardUiState("Futbol", true, 4, listOf("Abdominales", "Piernas", "Gemelos", "Cabeza", "Pelota"), "https://phantom-marca.unidadeditorial.es/4a48d118c4427fc01575ac7e16d4b4a0/crop/0x70/1022x644/resize/1320/f/jpg/assets/multimedia/imagenes/2021/07/11/16259717481572.jpg")
                ),
                createdRoutines = listOf(
                    RoutineCardUiState("Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
                    RoutineCardUiState("Yoga", false, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
                    RoutineCardUiState("Abdominales", false, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
                    RoutineCardUiState("Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
                    RoutineCardUiState("null", false, 0, listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")),
                )
            )
        }
        composable("Favorites"){
            FavoritesScreen(
                onNavigateToFavoritesScreen = {navController.navigate("Favorites")},
                onNavigateToHomeScreen = {navController.navigate("MainScreen")},
                onNavigateToProfileScreen = {navController.navigate("Profile")},
                onNavigateToRutineDetailScreen = {navController.navigate("RutineDetails")},
                onNavigateToResetHomeScreen = {navController.navigate("MainScreen"){
                    popUpTo("MainScreen")
                } },
                createdRoutines = listOf(
                    RoutineCardUiState("Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
                    RoutineCardUiState("Yoga", true, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
                    RoutineCardUiState("Abdominales", true, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
                    RoutineCardUiState("Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
                    RoutineCardUiState("Atletismo", true, 1, listOf("Piernas", "Exigente", "Cinta", "Bicicleta"), "https://concepto.de/wp-content/uploads/2015/03/atletismo-e1550017721661.jpg")
                )
            )
        }
        composable("RutineDetails"){
            RoutineDetail(RoutineDetailUiState("Futbol",3,"Jose",3,120000,listOf("Hola","Como","estas", "buenas","tardes","Futbol","Scaloneta","Messi"), cycles))
        }

    }
}