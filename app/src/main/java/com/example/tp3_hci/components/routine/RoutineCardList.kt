package com.example.tp3_hci.components.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R


// -----------------------------------------------------------------------------------
// modifier: Estilo a aplicar sobre el lazyColumn
// routines: La lista de rutinas a mostrar
// header: Componentes a mostrar por encima de la lista
// footer: Componentes a mostrar por debajo de la lista
// -----------------------------------------------------------------------------------
@Composable
fun RoutineCardList(
    onNavigateToRutineDetailScreen: ()-> Unit,
    modifier: Modifier = Modifier,
    routines: List<RoutineInfo>? = null,
    header: (@Composable ()->Unit)? = null,
    footer: (@Composable ()->Unit)? = null,
){
    val orderByItems : List<DropDownItem> = listOf(
        DropDownItem(stringResource(id = R.string.name)),
        DropDownItem(stringResource(id = R.string.creation_date)),
        DropDownItem(stringResource(id = R.string.rating)),
        DropDownItem(stringResource(id = R.string.difficulty)),
        DropDownItem(stringResource(id = R.string.category))
    )

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        item {
            if(header != null){
                header()
            }
        }

        item {
            RoutineOrderDropDown(
                items = orderByItems,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        // RoutineCardList
        if(routines != null){
            items(routines){ routine ->
                RoutineCard(
                    onNavigateToRutineDetailScreen,
                    routine = routine,
                    modifier = Modifier.padding(vertical = 10.dp),
                )
            }
        }

        item {
            if(footer != null){
                footer()
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun RoutineCardListPreview(

) {
    val routines = listOf(
        RoutineInfo("Fuerza", true, 4, listOf("Brazos", "Piernas", "Mancuernas", "Esfuerzo"), "https://cdn.vox-cdn.com/thumbor/XSW5TTZRjsqJgUeBu46g2zmn4uE=/0x0:5472x3648/1200x800/filters:focal(1554x1539:2428x2413)/cdn.vox-cdn.com/uploads/chorus_image/image/67453937/1224663515.jpg.0.jpg"),
        RoutineInfo("Yoga", false, 3, listOf("Espalda", "Piernas", "Estiramiento"), "https://www.cnet.com/a/img/resize/cf54eb3b6a32bf47369ab771584cbefeeb4479cd/hub/2022/02/02/f80a19b8-42a5-4c71-afa2-cb9d5df312cd/gettyimages-1291740163.jpg?auto=webp&width=1200"),
        RoutineInfo("Abdominales", false, 5, listOf("Abdominales"), "https://www.verywellfit.com/thmb/Cx-pCfa8rUDPfc9Nwg-JPx5xh44=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/91107761-56a2b58f3df78cf77279080c.jpg"),
        RoutineInfo("Velocidad", true, 2, listOf("Piernas", "Gemelos"), "https://wpassets.trainingpeaks.com/wp-content/uploads/2019/08/08162909/marathon-workout-blog-1200x675.jpg"),
        RoutineInfo("null", false, 0, listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")),
    )
    RoutineCardList(routines = routines)
}

 */