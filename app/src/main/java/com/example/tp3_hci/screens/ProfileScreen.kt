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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileNavigation: profileNavigation,
    setTopAppBar : ((TopAppBarType)->Unit),
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = getViewModelFactory())
){
    val UiState = viewModel.uiState
    if(!UiState.isFetching && UiState.currentUser==null && UiState.message==null){
        viewModel.getCurrentUser()
    }
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
                            .padding(top = 20.dp, bottom = 20.dp)
                            .fillMaxHeight(0.3f),
                        onImageUrlChange = null
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Pepe",
                        style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
                        color = FitiBlue,
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Divider(color = FitiBlue, thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))
                dataUser(Type= "Email: " ,data = UiState.currentUser?.email)
                dataUser(Type= "Nombre: " ,data = UiState.currentUser?.firstName)
                dataUser(Type= "Apellido: " ,data = UiState.currentUser?.lastName)
                dataUser(Type= "Cumpleaños: " ,data = UiState.currentUser?.birthdate.toString())
                Text(
                    text = "Preferencias",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    color = FitiBlueText,
                    modifier = Modifier
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Vista Simplificada: ",
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
fun LabelledSwitch( // (1)
    modifier: Modifier = Modifier,
    checked: Boolean,
    label: String,
    onCheckedChange: ((Boolean) -> Unit),
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors()
) {

    Box( // (2)
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .toggleable( // (4)
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Switch,
                enabled = enabled
            )
            .padding(horizontal = 16.dp)

    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides
                    if (enabled) ContentAlpha.high else ContentAlpha.disabled
        ) {
            Text( // (3)
                text = label,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 16.dp)
            )
        }

        Switch( // (3)
            checked = checked,
            onCheckedChange = null, // (4)
            enabled = enabled,
            colors = colors,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

/*
@Preview
@Composable
fun profileView(){
    Column(
        modifier = Modifier.padding(start = 20.dp)
    ) {
        Column(){
            ProfileAvatar(
                imageUrl = "https://www.elcolombiano.com/binrepository/829x565/49c0/780d565/none/11101/ASLQ/20221114-58cc9d3b63734727087f0540e61c0648179f8cd4_41058203_20221115111342.jpg",
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 30.dp)
                    .fillMaxHeight(0.3f),
                onImageUrlChange = null
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "USER NAME",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                color = FitiBlue,
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        dataUser(Type= "username: " ,data = "Pepe")
        dataUser(Type= "username2: " ,data = "Pepe2")
    }
}
*/

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
            modifier = Modifier
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