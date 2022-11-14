package com.example.tp3_hci.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_hci.R
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.ui.theme.TP3_HCITheme
import com.example.tp3_hci.utilities.WindowInfo
import com.example.tp3_hci.utilities.rememberWindowInfo


// -------------------------------------------------------------------------
// leftIcon: Componente que se desea colocar a la izquierda del TopBar (generalmente, es un IconButton)
// centerComponent: Componente que se desea insertar entre los componentes de la izquierda y la derecha (generalmente, es un Text o un TextField)
// secondRightIcon: Componente que se desea colocar a la derecha del TopBar pero no es el ultimo (generalmente, es un IconButton)
// rightIcon: Componente que se desea colocar a la derecha del TopBar (generalmente, es un IconButton)
// -------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    scrollBehavior: TopAppBarScrollBehavior,
    leftIcon: (@Composable () -> Unit?)? = null,
    centerComponent: (@Composable () -> Unit)? = null,
    secondRightIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    defaulNav: () ->Unit
) {
    val windowInfo = rememberWindowInfo()

    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (leftIcon != null) {
                leftIcon()
            } else if (windowInfo.screenWidthInfo !is WindowInfo.WindowType.Expanded) {
                Column(modifier = Modifier.clickable { defaulNav() }) {
                    Image(
                        painter = painterResource(id = R.drawable.fiti),
                        contentDescription = stringResource(id = R.string.fiti_logo),
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
                }
        },
        title = {
            if (centerComponent != null) {
                centerComponent()
            }
        },
        actions = {
            if (secondRightIcon != null) {
                secondRightIcon()
            }
            if (rightIcon != null) {
                rightIcon()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = FitiBlue,
            scrolledContainerColor = FitiBlue,
            navigationIconContentColor = FitiWhiteText,
            titleContentColor = FitiWhiteText,
            actionIconContentColor = FitiWhiteText
        )
    )
}

/*

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopNavigationBarPreview(){
    TP3_HCITheme {
        Scaffold(
            topBar = {
                TopNavigationBar(
                    scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                    leftIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    secondRightIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    rightIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    centerComponent = {
                        Text(
                            text = "FITI",
                            style = MaterialTheme.typography.h3.copy(fontSize = 22.sp)
                        )
                    }
                )
            }
        ){}
    }
}

*/