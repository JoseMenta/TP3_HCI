package com.example.tp3_hci.components.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tp3_hci.data.RoutineCardUiState
import com.example.tp3_hci.data.RoutineDetailUiState
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiGreenButton

@Composable
fun ratingView(
    onNavigateToHomeScreen: ()-> Unit,
    routine: RoutineCardUiState
){
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Congratulations(routine)
        valoration()
        ButtonSide(onNavigateToHomeScreen)

    }
}

@Composable
fun Congratulations(
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
fun valoration(){
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
fun ButtonSide(
    onNavigateToHomeScreen: () -> Unit
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
            onClick = {onNavigateToHomeScreen() },
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
            onClick = { onNavigateToHomeScreen()},
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
