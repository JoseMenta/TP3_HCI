package com.example.tp3_hci.components.routine

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.ui.theme.*

@Composable
fun RoutineTag(
    text: String,
    modifier: Modifier = Modifier
){
    Card(
        elevation = 10.dp,
        backgroundColor = FitiGrey,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            color = FitiBlueText,
            modifier = Modifier.
                padding(horizontal = 4.dp, vertical = 2.dp),
        )
    }
}