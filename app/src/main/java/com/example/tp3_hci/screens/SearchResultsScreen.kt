package com.example.tp3_hci.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.components.routine.RoutineCardDisplay
import com.example.tp3_hci.data.RoutineCardUiState
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.ui.theme.TP3_HCITheme
import com.example.tp3_hci.utilities.*


@Composable
fun SearchResultsScreen(
    stringSearched: String = "",
    routinesFound : List<RoutineCardUiState>? = null,
    onNavigateToRutineDetailScreen : (String) -> Unit,
) {
    val windowInfo = rememberWindowInfo()

    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact ||
        windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact){
        SearchResultsScreenMobile(
            stringSearched = stringSearched,
            routinesFound = routinesFound,
            onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen,
        )
    } else {
        SearchResultsScreenTablet(
            stringSearched = stringSearched,
            routinesFound = routinesFound,
            onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchResultsScreenTablet(
    stringSearched: String = "",
    routinesFound : List<RoutineCardUiState>? = null,
    onNavigateToRutineDetailScreen : (String) -> Unit,
){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    //RegularTabletDisplay(
    //    content = {
            RoutineCardDisplay(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                routines = routinesFound,
                header = {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.results_title, stringSearched),
                            style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
                            color = FitiBlueText,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                },
                onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen
            )
        //},
        //scrollBehavior = scrollBehavior
    //)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchResultsScreenMobile(
    stringSearched: String = "",
    routinesFound : List<RoutineCardUiState>? = null,
    onNavigateToRutineDetailScreen : (String) -> Unit,
){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    //RegularMobileDisplay(
    //    content = {
            RoutineCardDisplay(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                routines = routinesFound,
                header = {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.results_title, stringSearched),
                            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
                            color = FitiBlueText,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                },
                onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen
            )
        //},
        //scrollBehavior = scrollBehavior
    //)
}


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
            onNavigateToRutineDetailScreen = {},
        )
    }
}
