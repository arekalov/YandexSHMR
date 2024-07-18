package com.arekalov.yandexshmr.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.arekalov.yandexshmr.presentation.common.models.AppTheme

val LightScheme = lightColorScheme(
    primary = blueLight,
    onPrimary = Color.White,
    secondary = greenLight,
    onSecondary = Color.White,
    onTertiary = Color.White,
    tertiary = redLight,
    background = backPrimaryLight,
    surface = backSecondaryLight,
    onBackground = labelPrimaryLight,
    onSurface = labelPrimaryLight,
    onSurfaceVariant = labelDisableLight
)

val DarkScheme = darkColorScheme(
    primary = blueDark,
    onPrimary = Color.White,
    secondary = greenDark,
    onSecondary = Color.White,
    onTertiary = Color.White,
    tertiary = redDark,
    background = backPrimaryDark,
    surface = backSecondaryDark,
    onBackground = labelPrimaryDark,
    onSurface = labelPrimaryDark,
    onSurfaceVariant = labelDisableDark
)


@Composable
fun ToDoListTheme(
    theme: AppTheme = AppTheme.SYSTEM,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        if (theme == AppTheme.SYSTEM) {
            if (isSystemInDarkTheme()) {
                DarkScheme
            } else {
                LightScheme
            }
        } else if (theme == AppTheme.LIGHT) {
            LightScheme
        } else {
            DarkScheme
        }
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}