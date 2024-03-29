package com.example.tp3_hci.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.ui.theme.*


sealed class NavDrawerItem(
    val id: Int,
    val route: String,
    val icon: ImageVector,
    var selected: Boolean
) {
    object Home : NavDrawerItem(0,"MainScreen", Icons.Filled.Home, true)
    object Favorites: NavDrawerItem(1,"Favorites", Icons.Filled.Favorite, false)
    object Profile: NavDrawerItem(2,"Profile", Icons.Filled.Person, false)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    content: (@Composable ()->Unit)? = null,
    actions: (NavDrawerItem) ->Unit,
    restartSelectedNavigation: Boolean,
    route : String?
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.width(200.dp),
                drawerShape = RoundedCornerShape(bottomEnd = 16.dp),
                drawerContainerColor = FitiBlue,
                drawerContentColor = FitiWhiteText
            ) {
                DrawerHeader()
                DrawerBody(
                    restartSelectedNavigation = restartSelectedNavigation,
                    onItemClick = { /*TODO*/ },
                    actions = actions,
                    route = route
                )
            }

        }
    ){
        if(content != null){
            content()
        }
    }
}


@Composable
private fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp, horizontal = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fiti_logo),
            contentDescription = stringResource(id = R.string.fiti_logo),
            contentScale = ContentScale.Fit
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerBody(
    restartSelectedNavigation : Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (NavDrawerItem) -> Unit,
    actions : (NavDrawerItem) ->Unit,
    route : String?
) {
    val primaryItems = listOf(NavDrawerItem.Home, NavDrawerItem.Favorites, NavDrawerItem.Profile)

    // val backStackEntry by navController.currentBackStackEntryAsState()

    LazyColumn(
        modifier = modifier
    ){

            for(item in primaryItems){
                item.selected = item.route == route
            }

        items(primaryItems){ item ->
            // val selected = (item.route == backStackEntry?.destination?.route)
            //val selected = (item.id == NavDrawerItem.Home.id)

            NavigationDrawerItem(
                label = {
                    Text(
                        text = getTitleString(item = item),
                        style = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Bold),
                        color = FitiWhiteText
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = getContentDescriptionString(item = item),
                        tint = FitiWhiteText
                    )
                },
                selected = item.selected,
                modifier = Modifier.padding(horizontal = 10.dp),
                onClick = {
                    actions(item)
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = FitiDarkBlue,
                    unselectedContainerColor = FitiBlue,
                    selectedIconColor = FitiWhiteText,
                    unselectedIconColor = FitiWhiteText,
                    selectedTextColor = FitiWhiteText,
                    unselectedTextColor = FitiWhiteText,
                )
            )
        }

        item{
            Divider(
                thickness = 2.dp,
                color = FitiWhiteText,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            )
        }
    }
}


@Composable
private fun getTitleString(
    item : NavDrawerItem
): String {
    return when (item) {
        is NavDrawerItem.Home -> stringResource(R.string.nav_drawer_home)
        is NavDrawerItem.Favorites -> stringResource(id = R.string.nav_drawer_favorites)
        is NavDrawerItem.Profile -> stringResource(id = R.string.nav_drawer_profile)
        else -> stringResource(id = R.string.nav_drawer_signout)
    }
}

@Composable
private fun getContentDescriptionString(
    item : NavDrawerItem
): String {
    return when (item) {
        is NavDrawerItem.Home -> stringResource(R.string.nav_drawer_home_cd)
        is NavDrawerItem.Favorites -> stringResource(id = R.string.nav_drawer_favorites_cd)
        is NavDrawerItem.Profile -> stringResource(id = R.string.nav_drawer_profile_cd)
        else -> stringResource(id = R.string.nav_drawer_signout_cd)
    }
}

/*
@Preview(showBackground = true)
@Composable
fun NavigationDrawerPreview(){
    TP3_HCITheme() {
        NavigationDrawer()

    }
}

 */