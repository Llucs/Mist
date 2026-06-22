package com.llucs.mist.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = MistExpressiveColors.LightPrimary,
    onPrimary = MistExpressiveColors.LightOnPrimary,
    primaryContainer = MistExpressiveColors.LightPrimaryContainer,
    onPrimaryContainer = MistExpressiveColors.LightOnPrimaryContainer,
    secondary = MistExpressiveColors.LightSecondary,
    onSecondary = MistExpressiveColors.LightOnSecondary,
    secondaryContainer = MistExpressiveColors.LightSecondaryContainer,
    onSecondaryContainer = MistExpressiveColors.LightOnSecondaryContainer,
    tertiary = MistExpressiveColors.LightTertiary,
    onTertiary = MistExpressiveColors.LightOnTertiary,
    tertiaryContainer = MistExpressiveColors.LightTertiaryContainer,
    onTertiaryContainer = MistExpressiveColors.LightOnTertiaryContainer,
    error = MistExpressiveColors.LightError,
    onError = MistExpressiveColors.LightOnError,
    errorContainer = MistExpressiveColors.LightErrorContainer,
    onErrorContainer = MistExpressiveColors.LightOnErrorContainer,
    background = MistExpressiveColors.LightBackground,
    onBackground = MistExpressiveColors.LightOnBackground,
    surface = MistExpressiveColors.LightSurface,
    onSurface = MistExpressiveColors.LightOnSurface,
    surfaceVariant = MistExpressiveColors.LightSurfaceVariant,
    onSurfaceVariant = MistExpressiveColors.LightOnSurfaceVariant,
    outline = MistExpressiveColors.LightOutline,
    outlineVariant = MistExpressiveColors.LightOutlineVariant,
    inverseSurface = MistExpressiveColors.LightInverseSurface,
    inverseOnSurface = MistExpressiveColors.LightInverseOnSurface,
    inversePrimary = MistExpressiveColors.LightInversePrimary,
    scrim = MistExpressiveColors.LightScrim
)

private val DarkColorScheme = darkColorScheme(
    primary = MistExpressiveColors.DarkPrimary,
    onPrimary = MistExpressiveColors.DarkOnPrimary,
    primaryContainer = MistExpressiveColors.DarkPrimaryContainer,
    onPrimaryContainer = MistExpressiveColors.DarkOnPrimaryContainer,
    secondary = MistExpressiveColors.DarkSecondary,
    onSecondary = MistExpressiveColors.DarkOnSecondary,
    secondaryContainer = MistExpressiveColors.DarkSecondaryContainer,
    onSecondaryContainer = MistExpressiveColors.DarkOnSecondaryContainer,
    tertiary = MistExpressiveColors.DarkTertiary,
    onTertiary = MistExpressiveColors.DarkOnTertiary,
    tertiaryContainer = MistExpressiveColors.DarkTertiaryContainer,
    onTertiaryContainer = MistExpressiveColors.DarkOnTertiaryContainer,
    error = MistExpressiveColors.DarkError,
    onError = MistExpressiveColors.DarkOnError,
    errorContainer = MistExpressiveColors.DarkErrorContainer,
    onErrorContainer = MistExpressiveColors.DarkOnErrorContainer,
    background = MistExpressiveColors.DarkBackground,
    onBackground = MistExpressiveColors.DarkOnBackground,
    surface = MistExpressiveColors.DarkSurface,
    onSurface = MistExpressiveColors.DarkOnSurface,
    surfaceVariant = MistExpressiveColors.DarkSurfaceVariant,
    onSurfaceVariant = MistExpressiveColors.DarkOnSurfaceVariant,
    outline = MistExpressiveColors.DarkOutline,
    outlineVariant = MistExpressiveColors.DarkOutlineVariant,
    inverseSurface = MistExpressiveColors.DarkInverseSurface,
    inverseOnSurface = MistExpressiveColors.DarkInverseOnSurface,
    inversePrimary = MistExpressiveColors.DarkInversePrimary,
    scrim = MistExpressiveColors.DarkScrim
)

@Composable
fun MistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MistTypography,
        content = content
    )
}
