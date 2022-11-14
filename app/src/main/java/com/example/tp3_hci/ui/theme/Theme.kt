package com.example.tp3_hci.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = FitiBlue,
    primaryVariant = FitiDarkBlue,
    secondary = FitiGrey,
    background = Color.Black,
    surface = FitiGreyImage,
    onPrimary = FitiGreenButton
)

private val LightColorPalette = lightColors(
    primary = FitiBlue,
    primaryVariant = FitiDarkBlue,
    secondary = FitiGrey,
    background = Color.White,
    surface = FitiGreyImage,
    onPrimary = FitiGreenButton

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TP3_HCITheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}