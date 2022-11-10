package com.example.tp3_hci.components.routine

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.ui.theme.Shapes

@Composable
fun RoutineTag(
    tag: String
) {
    Card(
        modifier = Modifier
            .padding(3.dp)
            .clip(Shapes.medium),
        backgroundColor = MaterialTheme.colors.secondary,
    ) {
        Text(text = tag, modifier = Modifier.padding(6.dp), style = MaterialTheme.typography.h4)
    }
}