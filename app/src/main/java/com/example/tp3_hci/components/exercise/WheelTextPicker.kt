package com.example.tp3_hci.components.exercise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun WheelTextPicker(
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(128.dp, 128.dp),
    startIndex: Int = 0,
    texts: List<String>,
    textStyle: TextStyle = MaterialTheme.typography.h4,
    textColor: Color = LocalContentColor.current,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(12.dp),
    selectorColor: Color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
    selectorBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colors.primary),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
) {
    WheelPicker(
        modifier = modifier,
        size = size,
        startIndex = startIndex,
        count = texts.size,
        selectorEnabled = selectorEnabled,
        selectorShape = selectorShape,
        selectorColor = selectorColor,
        selectorBorder = selectorBorder,
        onScrollFinished = onScrollFinished
    ){ index, snappedIndex ->

        Text(
            text = texts[index],
            style = textStyle,
            color = textColor.copy(
                alpha = when(snappedIndex){
                    index + 1 -> {
                        0.4f
                    }
                    index -> {
                        1f
                    }
                    index - 1 -> {
                        0.4f
                    }
                    else -> 0.4f
                }
            ),
            maxLines = 1
        )
    }
}