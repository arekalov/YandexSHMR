package com.arekalov.yandexshmr.presentation.common.views


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbar(
    modifier: Modifier = Modifier,
    snackbarData: SnackbarData,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    actionColor: Color = MaterialTheme.colorScheme.primary,
) {
    Snackbar(
        snackbarData = snackbarData,
        containerColor = backgroundColor,
        contentColor = contentColor,
        actionColor = actionColor,
        modifier = modifier.padding(vertical = 35.dp, horizontal = 4.dp)
    )
}