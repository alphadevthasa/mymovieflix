package com.example.mymovieflix.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val NetflixDarkColorScheme = darkColorScheme(
    primary = NetflixRed,
    onPrimary = Color.White,
    primaryContainer = NetflixRedDark,
    onPrimaryContainer = Color.White,
    secondary = GreyMedium,
    onSecondary = Black,
    tertiary = GreyLight,
    background = NearBlack,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = Color.White,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = GreyMedium,
    outline = GreyDark
)

private val NetflixLightColorScheme = lightColorScheme(
    primary = NetflixRed,
    onPrimary = Color.White,
    primaryContainer = NetflixRed.copy(alpha = 0.2f),
    onPrimaryContainer = Black,
    secondary = GreyDark,
    onSecondary = Color.White,
    tertiary = GreyDark,
    background = Color.White,
    onBackground = Black,
    surface = Color.White,
    onSurface = Black,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = GreyDark,
    outline = GreyDark
)

@Composable
fun MyMovieFlixTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> NetflixDarkColorScheme
        else -> NetflixLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
