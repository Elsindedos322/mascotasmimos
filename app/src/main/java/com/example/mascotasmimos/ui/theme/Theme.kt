package com.example.mascotasmimos.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PastelPurple,
    onPrimary = TextPrimary,
    primaryContainer = PastelPurpleLight,
    onPrimaryContainer = PastelPurpleDark,
    secondary = PastelLavender,
    onSecondary = TextPrimary,
    secondaryContainer = PastelLavenderLight,
    onSecondaryContainer = PastelLavenderDark,
    background = BackgroundPrimary,
    onBackground = TextPrimary,
    surface = PastelPurpleLight,
    onSurface = TextPrimary,
    surfaceVariant = PastelLavenderLight,
    error = Color(0xFF9536F4),
    errorContainer = Color(0xFF8251D5)
)

private val DarkColorScheme = darkColorScheme(
    primary = PastelPurpleDark,
    onPrimary = Color.White,
    primaryContainer = PastelPurple,
    onPrimaryContainer = PastelPurpleLight,
    secondary = PastelLavenderDark,
    onSecondary = Color.White,
    secondaryContainer = PastelLavender,
    onSecondaryContainer = PastelLavenderLight,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = PastelPurpleDark,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF1E1A1F),
    error = Color(0xFF9266CF),
    errorContainer = Color(0xFF2F00B0)
)

@Composable
fun MascotasmimosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}