package com.hoppswap.core.designsystem.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val BrandPrimary = Color(0xFF00CEC8)
val BrandBackground = Color(0xFFFEF9F9)
val BrandOnBackground = Color(0xFF0B0B0B)
val BrandSurface = Color(0xFFFEF9F9)
val BrandOnSurface = Color(0xFF0B0B0B)
val BrandError = Color(0xFFD32F2F)
val BrandOnError = Color.White

val LightColors = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,
    background = BrandBackground,
    onBackground = BrandOnBackground,
    surface = BrandSurface,
    onSurface = BrandOnSurface,
    error = BrandError,
    onError = BrandOnError,
)

val DarkColors = darkColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.Black,
    background = BrandOnBackground,
    onBackground = BrandBackground,
    surface = BrandOnSurface,
    onSurface = BrandBackground,
    error = BrandError,
    onError = BrandOnError,
)

