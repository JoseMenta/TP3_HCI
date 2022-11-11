package com.example.tp3_hci.components.navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.ui.theme.TP3_HCITheme

@Composable
fun TopNavigationBar(
    leftIcon: (@Composable () -> Unit?)? = null,
    centerComponent: (@Composable () -> Unit)? = null,
    secondRightIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null
) {
    TopAppBar(
        backgroundColor = FitiBlue,
        contentColor = FitiWhiteText,
        navigationIcon = {
            if (leftIcon != null) {
                leftIcon()
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
