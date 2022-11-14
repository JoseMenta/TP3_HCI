package com.example.tp3_hci.utilities

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.BottomNavItem
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.NavigationDrawer
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.search.SearchFiltersSurface
import com.example.tp3_hci.components.search.SearchTopBar
import com.example.tp3_hci.ui.theme.FitiWhiteText


sealed class TopAppBarState(){
    object Regular: TopAppBarState()
    object Search: TopAppBarState()
}

sealed class RegularBottomNavItem(
    override val nameId: Int,
    override val route: String,
    override val icon: ImageVector
): BottomNavItem(
    nameId = nameId,
    route = route,
    icon = icon
){
    object Favorites : RegularBottomNavItem(R.string.bottom_nav_favorites, "Favorites", Icons.Filled.Favorite)
    object Home : RegularBottomNavItem(R.string.bottom_nav_home, "MainScreen", Icons.Filled.Home)
    object Profile : RegularBottomNavItem(R.string.bottom_nav_profile, "Profile", Icons.Filled.Person)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegularMobileDisplay(
    content: @Composable ()->Unit,
    scrollBehavior: TopAppBarScrollBehavior
){
    var topAppBarState by remember { mutableStateOf(TopAppBarState.Regular as TopAppBarState) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            BottomNavigationBar(
                items = RegularBottomNavItem::class.sealedSubclasses.map {
                    subclass -> subclass.objectInstance as BottomNavItem
                },
                navController = rememberNavController()
            )
        },
        topBar = {
            RegularTopAppBar(
                scrollBehavior = scrollBehavior,
                topAppBarState = topAppBarState,
                onTopAppBarState = { newState ->
                    topAppBarState = newState
                }
            )
        }
    ){
        Box(
            modifier = Modifier.padding(it)
        ){
            content()

            AnimatedVisibility(
                visible = (topAppBarState is TopAppBarState.Search),
                enter = expandVertically(
                    expandFrom = Alignment.Top
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top
                )
            ) {
                SearchFiltersSurface()
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegularTabletDisplay(
    content: @Composable ()->Unit,
    scrollBehavior: TopAppBarScrollBehavior
){
    var topAppBarState by remember { mutableStateOf(TopAppBarState.Regular as TopAppBarState) }

    NavigationDrawer(
        content = {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    RegularTopAppBar(
                        scrollBehavior = scrollBehavior,
                        topAppBarState = topAppBarState,
                        onTopAppBarState = { newState ->
                            topAppBarState = newState
                        }
                    )
                }
            ){
                Box(
                    modifier = Modifier.padding(it)
                ){
                    content()

                    AnimatedVisibility(
                        visible = (topAppBarState is TopAppBarState.Search),
                        enter = expandVertically(
                            expandFrom = Alignment.Top
                        ),
                        exit = shrinkVertically(
                            shrinkTowards = Alignment.Top
                        )
                    ) {
                        SearchFiltersSurface()
                    }
                }
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegularTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    topAppBarState: TopAppBarState,
    onTopAppBarState: (TopAppBarState) -> Unit
) {
    var searchInput by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        SearchTopBar(
            scrollBehavior = scrollBehavior,
            input = searchInput,
            onCloseSearchBarState = {
                onTopAppBarState(TopAppBarState.Regular)
            },
            onInputChange = { newInput ->
                searchInput = newInput
            },
            onSearchClicked = {
                println("Se busco $it")
            }
        )

        AnimatedVisibility(
            visible = (topAppBarState is TopAppBarState.Regular),
            enter = expandHorizontally(
                expandFrom = Alignment.Start
            ),
            exit = shrinkHorizontally(
                shrinkTowards = Alignment.Start
            )
        ) {
            TopNavigationBar(
                scrollBehavior = scrollBehavior,
                rightIcon = {
                    IconButton(onClick = {
                        onTopAppBarState(TopAppBarState.Search)
                    }) {
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
                        text = stringResource(id = R.string.fiti),
                        style = MaterialTheme.typography.h2,
                        color = FitiWhiteText
                    )
                }
            )
        }
    }
}