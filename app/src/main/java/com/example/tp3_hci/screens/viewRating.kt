package com.example.tp3_hci.screens

import android.content.ClipboardManager
import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import com.example.tp3_hci.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.components.review.RatingBar
import com.example.tp3_hci.data.RoutineCardUiState
import com.example.tp3_hci.ui.theme.FitiBlack
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiGreenButton
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.utilities.NavigationUtilities
import com.example.tp3_hci.utilities.TopAppBarType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingView(
    navigationUtilities: NavigationUtilities,
    setTopAppBar : ((TopAppBarType)->Unit),
    routine: RoutineCardUiState
){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    setTopAppBar(
        TopAppBarType(
            topAppBar = {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    title = routine.name,
                    navigationUtilities = navigationUtilities
                )
            }
        )
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Congratulations(routine)
        Spacer(modifier = Modifier.height(20.dp))
        Valoration()
        Spacer(modifier = Modifier.height(20.dp))
        ShareURL(routine = routine)
        Spacer(modifier = Modifier.height(20.dp))
        ButtonSide(navigationUtilities)
    }
}

@Composable
private fun ShareURL(
    routine : RoutineCardUiState
) {
    val clipboardManager: androidx.compose.ui.platform.ClipboardManager =
        LocalClipboardManager.current

    Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,) {

        Text(
            text = "Comparti esta Rutina",
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Card( border = BorderStroke(2.dp, FitiBlack),
            modifier = Modifier,
            backgroundColor = Color.White
        ) {
            Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp) ) {
                val route = "https://fiti.com/RoutineDetails/${routine.id}"
                Text(
                    text = route,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                IconButton(onClick = {
                    clipboardManager.setText(AnnotatedString(route))
                }){
                    Icon(painter = rememberVectorPainter(Icons.Rounded.CopyAll), contentDescription = "copy")
                }
            }
        }
    }
}


@Composable
private fun Congratulations(
    routine: RoutineCardUiState
){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = routine.imageUrl,
            contentDescription = "Rutina",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Felicitaciones!",
            style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
        Text(
            text = "Completaste la rutina ${routine.name}",
            color = Color.Black
        )
    }
}


@Composable
private fun Valoration(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Dejanos tu valoracion de los ejercicios",
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Row() {
            RatingBar(Modifier, 0)
        }
    }
}


@Composable
private fun ButtonSide(
    navigationUtilities: NavigationUtilities
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = FitiGreenButton,
            ),
            onClick = { navigationUtilities.navigateToRoute("MainScreen") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
        ) {
            Text(
                text = "Guardar",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = FitiBlue,
            ),
            onClick = { navigationUtilities.navigateToRoute("MainScreen") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
        ) {
            Text(
                text = "Ahora No",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    navigationUtilities: NavigationUtilities,
    scrollBehavior: TopAppBarScrollBehavior,
    title: String
){

    TopNavigationBar(
        scrollBehavior = scrollBehavior,
        leftIcon = {
            IconButton(onClick = {
                navigationUtilities.navigateToPreviousScreen()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.search),
                    tint = FitiWhiteText,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        centerComponent = {
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                color = FitiWhiteText
            )
        }
    )
}

