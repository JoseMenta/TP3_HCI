package com.example.tp3_hci.utilities

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.search.SearchFiltersSurface
import com.example.tp3_hci.components.search.SearchTopBar
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.utilities.navigation.SearchNavigation
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


class TopAppBarType(
    private val topAppBar: (@Composable ()->Unit)?
){
    fun getTopAppBar(): @Composable() (() -> Unit)? {
        return this.topAppBar
    }
}


sealed class TopAppBarState(){
    object Regular: TopAppBarState()
    object Search: TopAppBarState()
}


@Composable
fun RegularDisplay(
    content: @Composable ()->Unit,
    topAppBarState: TopAppBarState,
    hasSearch : Boolean = false,
    hasSwipeRefresh : Boolean = false,
    isRefreshing : Boolean = false,
    onRefreshSwipe : ()->Unit = {}
){
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxHeight()
    ){
        if(hasSwipeRefresh){
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = {
                    onRefreshSwipe()
                },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = FitiWhiteText,
                        backgroundColor = FitiBlue
                    )
                }
            ){
                content()
            }
        } else {
            content()
        }


        if(hasSearch){
            AnimatedVisibility(
                visible = (topAppBarState is TopAppBarState.Search),
                enter = expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top
                ) + fadeOut()
            ) {
                SearchFiltersSurface()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegularTopAppBar(
    searchNavigation: SearchNavigation,
    scrollBehavior: TopAppBarScrollBehavior,
    topAppBarState: TopAppBarState,
    onTopAppBarState: (TopAppBarState) -> Unit,
    leftIcon : (@Composable ()->Unit)? = null
) {
    var searchInput by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(topAppBarState is TopAppBarState.Search){
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
                    searchNavigation.getSearchScreen().invoke(it)
                }
            )
        }

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
                },
                leftIcon = leftIcon
            )
        }
    }
}


@Composable
fun ErrorSnackBar(
    scaffoldState : ScaffoldState,
    message : String,
    onActionLabelClicked: ()->Unit
){
    val dismissString = stringResource(id = R.string.snackbar_dismiss)

    // Muestra un snackbar
    LaunchedEffect(scaffoldState.snackbarHostState){
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = dismissString,
            duration = SnackbarDuration.Indefinite
        )
        when (result) {
            SnackbarResult.Dismissed -> onActionLabelClicked()
            SnackbarResult.ActionPerformed -> onActionLabelClicked()
        }
    }
}
