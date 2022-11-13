package com.example.tp3_hci.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_hci.R
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.ui.theme.TP3_HCITheme


// -------------------------------------------------------------------------
// leftIcon: Componente que se desea colocar a la izquierda del TopBar (generalmente, es un IconButton)
// centerComponent: Componente que se desea insertar entre los componentes de la izquierda y la derecha (generalmente, es un Text o un TextField)
// secondRightIcon: Componente que se desea colocar a la derecha del TopBar pero no es el ultimo (generalmente, es un IconButton)
// rightIcon: Componente que se desea colocar a la derecha del TopBar (generalmente, es un IconButton)
// -------------------------------------------------------------------------
@Composable
fun TopNavigationBar(
    leftIcon: (@Composable () -> Unit?)? = null,
    centerComponent: (@Composable () -> Unit)? = null,
    secondRightIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    defaulNav: () ->Unit
) {
    TopAppBar(
        backgroundColor = FitiBlue,
        contentColor = FitiWhiteText,
        navigationIcon = {
            if (leftIcon != null) {
                leftIcon()
            } else {
                Button(onClick = {
                    defaulNav()
                }) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.fiti),
                            contentDescription = "Logo FITI",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        },
        title = {
            if (centerComponent != null) {
                centerComponent()
            }
        },
        elevation = 10.dp,
        actions = {
            if (secondRightIcon != null) {
                secondRightIcon()
            }
            if (rightIcon != null) {
                rightIcon()
            }
        }
    )
}

/*
@Preview(showBackground = true)
@Composable
fun TopNavigationBarPreview(){
    TP3_HCITheme {
        Scaffold(
            topBar = {
                TopNavigationBar(
                    leftIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Menu",
                                tint = FitiWhiteText
                            )
                        }
                    },
                    secondRightIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Menu",
                                tint = FitiWhiteText
                            )
                        }
                    },
                    rightIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Menu",
                                tint = FitiWhiteText
                            )
                        }
                    },
                    centerComponent = {
                        Text(
                            text = "FITI",
                            style = MaterialTheme.typography.h3.copy(fontSize = 22.sp, color = FitiWhiteText)
                        )
                    }
                )
            }
        ){}
    }
}

*/