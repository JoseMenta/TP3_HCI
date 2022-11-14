package com.example.tp3_hci.components.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.data.DropDownItem
import com.example.tp3_hci.data.OrderByItem
import com.example.tp3_hci.data.RoutineCardUiState
import com.example.tp3_hci.utilities.WindowInfo
import com.example.tp3_hci.utilities.rememberWindowInfo


// -----------------------------------------------------------------------------------
// modifier: Estilo a aplicar sobre el lazyColumn
// routines: La lista de rutinas a mostrar
// header: Componentes a mostrar por encima de la lista
// footer: Componentes a mostrar por debajo de la lista
// -----------------------------------------------------------------------------------
@Composable
fun RoutineCardDisplay(
    modifier: Modifier = Modifier,
    routines: List<RoutineCardUiState>? = null,
    header: (@Composable ()->Unit)? = null,
    footer: (@Composable ()->Unit)? = null,
    onNavigateToRutineDetailScreen : () -> Unit
){
    val windowInfo = rememberWindowInfo()
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
        RoutineCardLazyList(
            modifier = modifier,
            routines = routines,
            header = header,
            footer = footer,
            onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen
        )
    }
    else if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded &&
            windowInfo.screenHeightInfo !is WindowInfo.WindowType.Expanded) {
        RoutineCardLazyGrid(
            itemsPerGrid = 3,
            modifier = modifier,
            routines = routines,
            header = header,
            footer = footer,
            onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen
        )
    } else {
        RoutineCardLazyGrid(
            itemsPerGrid = 2,
            modifier = modifier,
            routines = routines,
            header = header,
            footer = footer,
            onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen
        )
    }
}

@Composable
private fun RoutineCardLazyGrid(
    itemsPerGrid: Int,
    modifier: Modifier = Modifier,
    routines: List<RoutineCardUiState>? = null,
    header: (@Composable ()->Unit)? = null,
    footer: (@Composable ()->Unit)? = null,
    onNavigateToRutineDetailScreen : () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(itemsPerGrid),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        if(header != null){
            item(
                span = { GridItemSpan(itemsPerGrid) }
            ) {
                header()
            }
        }

        item (
            span = { GridItemSpan(itemsPerGrid) }
        ) {
            RoutineOrderDropDown(
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        // RoutineCardGrid
        if(routines != null){
            items(routines) { routine ->
                RoutineCard(
                    routine = routine,
                    modifier = Modifier.padding(vertical = 10.dp),
                    onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen
                )
            }
        }

        if(footer != null){
            item(
                span = { GridItemSpan(itemsPerGrid) }
            ) {
                footer()
            }
        }
    }
}


@Composable
private fun RoutineCardLazyList(
    modifier: Modifier = Modifier,
    routines: List<RoutineCardUiState>? = null,
    header: (@Composable ()->Unit)? = null,
    footer: (@Composable ()->Unit)? = null,
    onNavigateToRutineDetailScreen : () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        if(header != null){
            item {
                header()
            }
        }

        item {
            RoutineOrderDropDown(
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        // RoutineCardList
        if(routines != null){
            items(routines){ routine ->
                RoutineCard(
                    routine = routine,
                    modifier = Modifier.padding(vertical = 10.dp),
                    onNavigateToRutineDetailScreen = onNavigateToRutineDetailScreen
                )
            }
        }

        if(footer != null){
            item {
                footer()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RoutineCardDisplayPreview(

) {
    val routines = listOf(
        RoutineCardUiState("Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
        RoutineCardUiState("Yoga", false, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
        RoutineCardUiState("Abdominales", false, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
        RoutineCardUiState("Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
        RoutineCardUiState("null", false, 0, listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")),
    )
    RoutineCardDisplay(
        routines = routines,
        onNavigateToRutineDetailScreen = {}
    )
}