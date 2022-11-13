package com.example.tp3_hci.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.tp3_hci.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiGreenButton
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.ui.theme.TP3_HCITheme

data class BottomNavItem(
    val name: String,
    val nav: () -> Unit,
    val icon: ImageVector
)

// -------------------------------------------------------------------------
// Componente para la navegacion entre las pantallas principales
// TODO: Implementar navegacion
// -------------------------------------------------------------------------
@Composable
fun BottomNavigationBar(
    items : List<BottomNavItem>,
    // navController : NavController,
    modifier: Modifier = Modifier,
    // onItemClick: (BottomNavItem) -> Unit
){
    // val backStackEntry by navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = FitiBlue,
        elevation = 10.dp
    ) {
        items.forEach { item ->
            // val selected = (item.route == backStackEntry?.destination?.route)
            val selected = (item.name == "Inicio")
            BottomNavigationItem(
                selected = selected,
                onClick = {},
                selectedContentColor = FitiGreenButton,
                unselectedContentColor = FitiWhiteText,
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(onClick = { item.nav()}){
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                            )
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.caption,
                                textAlign = TextAlign.Center
                            )
                        }
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