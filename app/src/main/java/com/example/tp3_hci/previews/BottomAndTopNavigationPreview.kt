package com.example.tp3_hci.previews

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tp3_hci.R
import com.example.tp3_hci.components.navigation.BottomNavItem
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.ui.theme.TP3_HCITheme

@Preview(showBackground = true)
@Composable
fun BottomAndTopNavigationBarPreview() {
    val items : List<BottomNavItem> = listOf(
        BottomNavItem(stringResource(id = R.string.bottom_nav_favorites), "/favorites", Icons.Filled.Favorite),
        BottomNavItem(stringResource(id = R.string.bottom_nav_home), "/home", Icons.Filled.Home),
        BottomNavItem(stringResource(id = R.string.bottom_nav_profile), "/profile", Icons.Filled.Person)
    )

    TP3_HCITheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(items = items)
            },
            topBar = {
                TopNavigationBar(
                    leftIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Menu",
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