package com.example.tp3_hci.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiGreenButton
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.utilities.NavigationUtilities

open class BottomNavItem(
    open val nameId: Int,
    open val route: String,
    open val icon: ImageVector
)

sealed class RegularBottomNavItem(
    override val nameId: Int,
    override val route: String,
    override val icon: ImageVector
): BottomNavItem(
    nameId = nameId,
    route = route,
    icon = icon
){
    // TODO: Cambiar la ruta de Profile
    object Favorite : RegularBottomNavItem(R.string.bottom_nav_favorites, "Favorites", Icons.Filled.Favorite)
    object Home: RegularBottomNavItem(R.string.bottom_nav_home, "MainScreen", Icons.Filled.Home)
    object Profile : RegularBottomNavItem(R.string.bottom_nav_profile, "Favorites", Icons.Filled.Person)

    fun getBottomNavItems(): List<BottomNavItem>{
        return RegularBottomNavItem::class.sealedSubclasses.map{
            subclass -> subclass.objectInstance as BottomNavItem
        }
    }
}

// -------------------------------------------------------------------------
// Componente para la navegacion entre las pantallas principales
// -------------------------------------------------------------------------
@Composable
fun BottomNavigationBar(
    navigationUtilities: NavigationUtilities,
    items : List<BottomNavItem>,
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
                onClick = { navigationUtilities.navigateToRoute(item.route) },
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