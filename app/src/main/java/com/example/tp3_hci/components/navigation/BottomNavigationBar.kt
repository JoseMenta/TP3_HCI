package com.example.tp3_hci.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiGreenButton
import com.example.tp3_hci.ui.theme.FitiWhiteText

open class BottomNavItem(
    open val nameId: Int,
    open val route: String,
    open val icon: ImageVector
)

// -------------------------------------------------------------------------
// Componente para la navegacion entre las pantallas principales
// TODO: Implementar navegacion
// -------------------------------------------------------------------------
@Composable
fun BottomNavigationBar(
    items : List<BottomNavItem>,
    navController : NavController,
    modifier: Modifier = Modifier
){
    BottomNavigation(
        modifier = modifier,
        backgroundColor = FitiBlue,
        elevation = 10.dp
    ) {
        items.forEach { item ->
            val selected = (stringResource(id = item.nameId) == "Inicio")
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route)
                },
                selectedContentColor = FitiGreenButton,
                unselectedContentColor = FitiWhiteText,
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(id = item.nameId),
                        )

                        Text(
                            text = stringResource(id = item.nameId),
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    }
}

/*
@Composable
fun BottomNavigationBarPreview(
    onNavigateToFavScreen: (String) -> Unit,
) {
    val items : List<BottomNavItem> = listOf(
        BottomNavItem(stringResource(id = R.string.bottom_nav_favorites), "Fav", Icons.Filled.Favorite),
        BottomNavItem(stringResource(id = R.string.bottom_nav_home), "home", Icons.Filled.Home),
        BottomNavItem(stringResource(id = R.string.bottom_nav_profile), "profile", Icons.Filled.Person)
    )
    
    TP3_HCITheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(onNavigateToFavScreen, items = items)
            }
        ){}
    }
}
*/