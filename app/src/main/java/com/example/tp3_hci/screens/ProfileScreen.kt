package com.example.tp3_hci.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.profile.ProfileAvatar
import com.example.tp3_hci.data.view_model.ProfileViewModel
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.util.getViewModelFactory
import com.example.tp3_hci.utilities.*
import com.example.tp3_hci.utilities.navigation.profileNavigation
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileNavigation: profileNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = getViewModelFactory())
){
    val UiState = viewModel.uiState
    var topAppBarState by remember {
        mutableStateOf(TopAppBarState.Regular as TopAppBarState)
    }

    //if(!UiState.isFetching && UiState.currentUser==null && UiState.message==null){
        //viewModel.getCurrentUser()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
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
    //}



    RegularDisplay(
        content = {
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            ) {
                Column() {
                    var imgSrc = UiState.currentUser?.avatarUrl
                    ProfileAvatar(
                        imageUrl = imgSrc,
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 10.dp)
                            .fillMaxHeight(0.3f),
                        onImageUrlChange = null
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    UiState.currentUser?.username?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
                            color = FitiBlue,
                            modifier = Modifier
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = FitiBlue, thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))
                dataUser(Type= stringResource(id = R.string.email_type) ,data = UiState.currentUser?.email)
                dataUser(Type= stringResource(id = R.string.name_type) ,data = UiState.currentUser?.firstName)
                dataUser(Type= stringResource(id = R.string.surname_type) ,data = UiState.currentUser?.lastName)
                dataUser(Type= stringResource(id = R.string.birthday_type) ,data = UiState.currentUser?.birthdate?.let {SimpleDateFormat("dd/MM/yyy").format(it)})
                Text(
                    text = stringResource(id = R.string.preferencies),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    color = FitiBlueText,
                    modifier = Modifier
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(id = R.string.simplify),
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                        color = FitiBlueText,
                        modifier = Modifier
                    )
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = viewModel.getSimplify(),
                        onCheckedChange = { viewModel.changeSimplify() },
                    )
                }

            }
        },
        topAppBarState = topAppBarState
    )
}





@Composable
fun dataUser(
    Type: String,
    data: String?
) {
    Row() {
        Text(
            text = Type,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            color = FitiBlueText,
            modifier = Modifier.padding(start = 10.dp)
        )
        if (data != null) {
            Text(
                text = data,
                style = MaterialTheme.typography.h5.copy(),
                fontStyle = Italic,
                color = FitiBlueText,
                modifier = Modifier
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Divider(color = FitiBlue, thickness = 1.dp)
    Spacer(modifier = Modifier.height(10.dp))
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