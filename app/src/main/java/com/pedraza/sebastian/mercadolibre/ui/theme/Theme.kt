package com.pedraza.sebastian.mercadolibre.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.pedraza.sebastian.core.dimensions.Dimensions
import com.pedraza.sebastian.core.dimensions.LocalSpacing

private val DarkColorPalette = darkColors(
    primary = MeliYellow0,
    primaryVariant = MeliYellow1,
    secondary = MeliBlue0,
    background = MeliBlack0,
    onBackground = MeliWhite0,
    surface = Neutral7,
    onSurface = MeliWhite0,
    onPrimary = MeliBlack1,
    onSecondary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = MeliYellow0,
    primaryVariant = MeliYellow1,
    secondary = MeliBlue0,
    background = MeliWhite0,
    onBackground = MeliBlack0,
    surface = Neutral0,
    onSurface = MeliBlack1,
    onPrimary = MeliBlack1,
    onSecondary = Color.White,
)

@Composable
fun MercadoLibreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
