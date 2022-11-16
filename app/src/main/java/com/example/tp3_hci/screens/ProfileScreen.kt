package com.example.tp3_hci.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.routine.RoutineCardDisplay
import com.example.tp3_hci.data.view_model.LoginViewModel
import com.example.tp3_hci.data.view_model.ProfileViewModel
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.util.getViewModelFactory
import com.example.tp3_hci.utilities.RegularMobileDisplay
import com.example.tp3_hci.utilities.RegularTopAppBar
import com.example.tp3_hci.utilities.TopAppBarState
import com.example.tp3_hci.utilities.TopAppBarType
import com.example.tp3_hci.utilities.navigation.LoginNavigation
import com.example.tp3_hci.utilities.navigation.MainScreenNavigation
import com.example.tp3_hci.utilities.navigation.ViewRatingNavigation
import com.example.tp3_hci.utilities.navigation.profileNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileNavigation: profileNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = getViewModelFactory())
){
    val UiState = viewModel.uiState
    viewModel.getCurrentUser()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var topAppBarState by remember {
        mutableStateOf(TopAppBarState.Regular as TopAppBarState)
    }
    setTopAppBar(
        TopAppBarType(
            topAppBar = {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    profileNavigation = profileNavigation,
                    viewModel = viewModel
                )
            }
        )
    )

    RegularMobileDisplay(
        content = {
            Column() {
                Text(text = "username: ${UiState.currentUser?.username}")
                Text(text = "email: ${UiState.currentUser?.email}")
                Text(text = "id: ${UiState.currentUser?.id}")
                Text(text = "name ${UiState.currentUser?.firstName}")
                Text(text = "name ${UiState.currentUser?.lastName}")
            }
        },
        topAppBarState = topAppBarState
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    profileNavigation: profileNavigation,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: ProfileViewModel
){
    val UiState = viewModel.uiState
    if(UiState.isAuthenticated == false){
        profileNavigation.getLoginNavigation().invoke()
    }
    TopNavigationBar(
        scrollBehavior = scrollBehavior,
        rightIcon = {
            IconButton(onClick = {
                viewModel.logout()
                //while(viewModel.uiState.isFetching == true){}
                //profileNavigation.getLoginNavigation().invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
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
    )
}