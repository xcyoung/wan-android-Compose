package me.xcyoung.wan.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFffffff),
    primaryVariant = Color(0xFFcccccc),
    secondary = Color(0xFF6b38fb),
    secondaryVariant = Color(0xFF2100c7),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFFFFFFFF),
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFffffff),
    primaryVariant = Color(0xFFcccccc),
    secondary = Color(0xFF6b38fb),
    secondaryVariant = Color(0xFF2100c7),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFFFFFFFF),
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
fun WanAndroidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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