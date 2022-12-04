package com.pedraza.sebastian.core.dimensions

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Permitted Mercado libre dimensions provided through CompositionLocal
 */

data class Dimensions(
    val mercadoLibreSpacing0dp: Dp = 0.dp,
    val mercadoLibreSpacing4dp: Dp = 4.dp,
    val mercadoLibreSpacing8dp: Dp = 8.dp,
    val mercadoLibreSpacing16dp: Dp = 16.dp,
    val mercadoLibreSpacing22dp: Dp = 22.dp,
    val mercadoLibreSpacing32dp: Dp = 32.dp,
    val mercadoLibreSpacing64dp: Dp = 64.dp,
    val mercadoLibreSpacing100dp: Dp = 100.dp,
    val mercadoLibreSpacing150dp: Dp = 150.dp,
    val mercadoLibreSpacing200dp: Dp = 200.dp,
    val mercadoLibreSpacing250dp: Dp = 250.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }