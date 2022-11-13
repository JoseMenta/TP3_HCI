package com.example.tp3_hci.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.BottomNavItem
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.routine.*
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.ui.theme.TP3_HCITheme

@Composable
fun FavoritesScreen(
    onNavigateToFavoritesScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToProfileScreen : () -> Unit,
    onNavigateToResetHomeScreen : () -> Unit,
    onNavigateToRutineDetailScreen : () -> Unit,
    createdRoutines : List<RoutineInfo>? = null
){

    val bottomNavItems : List<BottomNavItem> = listOf(
        BottomNavItem(stringResource(id = R.string.bottom_nav_favorites), onNavigateToFavoritesScreen, Icons.Filled.Favorite),
        BottomNavItem(stringResource(id = R.string.bottom_nav_home), onNavigateToHomeScreen, Icons.Filled.Home),
        BottomNavItem(stringResource(id = R.string.bottom_nav_profile), onNavigateToProfileScreen, Icons.Filled.Person)
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(items = bottomNavItems)
        },
        topBar = {
            TopNavigationBar(
                rightIcon = {
                    IconButton(onClick = { onNavigateToResetHomeScreen()}) {
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
                        text = "FITI",
                        style = MaterialTheme.typography.h3.copy(fontSize = 22.sp),
                        color = FitiWhiteText
                    )
                },
                defaulNav = onNavigateToResetHomeScreen
            )
        }
    ){
        RoutineCardList(
            onNavigateToRutineDetailScreen,
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp),
            routines = createdRoutines,
            header = {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.favorites),
                        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
                        color = FitiBlueText,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }
        )
    }
}

/*

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview(){
    TP3_HCITheme {
        FavoritesScreen(
            createdRoutines = listOf(
                RoutineInfo("Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
                RoutineInfo("Yoga", true, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
                RoutineInfo("Abdominales", true, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
                RoutineInfo("Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
                RoutineInfo("Atletismo", true, 1, listOf("Piernas", "Exigente", "Cinta", "Bicicleta"), "https://concepto.de/wp-content/uploads/2015/03/atletismo-e1550017721661.jpg")
            )
        )
    }
}

*/
