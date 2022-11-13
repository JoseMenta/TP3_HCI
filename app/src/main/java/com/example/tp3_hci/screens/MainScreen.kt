package com.example.tp3_hci.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.BottomNavItem
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.routine.*
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.ui.theme.TP3_HCITheme
import androidx.compose.ui.text.font.FontWeight
import com.example.tp3_hci.ui.theme.FitiWhiteText

@Composable
fun MainScreen(
    onNavigateToFavoritesScreen : () -> Unit,
    onNavigateToHomeScreen : () -> Unit,
    onNavigateToProfileScreen : () -> Unit,
    onNavigateToResetHomeScreen : () -> Unit,
    onNavigateToRutineDetailScreen: () ->Unit,
    lastRoutineDone : List<RoutineInfo>? = null,
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
                    IconButton(onClick = { onNavigateToResetHomeScreen() }) {
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
                    if (lastRoutineDone != null) {
                        Text(
                            text = stringResource(id = R.string.last_routine_done),
                            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
                            color = FitiBlueText,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        RoutineCard(
                            onNavigateToRutineDetailScreen,
                            routine = lastRoutineDone[0],
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.created_routined),
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
fun MainScreenPreview(){
    TP3_HCITheme {
        MainScreen(
            lastRoutineDone = listOf(
                RoutineInfo("Futbol", true, 4, listOf("Abdominales", "Piernas", "Gemelos", "Cabeza", "Pelota"), "https://phantom-marca.unidadeditorial.es/4a48d118c4427fc01575ac7e16d4b4a0/crop/0x70/1022x644/resize/1320/f/jpg/assets/multimedia/imagenes/2021/07/11/16259717481572.jpg")
            ),
            createdRoutines = listOf(
                    RoutineInfo("Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
                    RoutineInfo("Yoga", false, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
                    RoutineInfo("Abdominales", false, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
                    RoutineInfo("Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
                    RoutineInfo("null", false, 0, listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")),
            )
        )
    }
}
*/