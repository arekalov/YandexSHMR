package com.arekalov.yandexshmr.presentation.settings.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@Composable
fun SetThemeItem(
    onClick: () -> Unit,
    nowTheme: AppTheme,
    modifier: Modifier = Modifier
) {
    TextButton(onClick = onClick) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.appThemeLabel),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = when (nowTheme) {
                    AppTheme.SYSTEM -> stringResource(R.string.SystemTheme)
                    AppTheme.DARK -> stringResource(R.string.DarkTheme)
                    AppTheme.LIGHT -> stringResource(R.string.LightTheme)
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun SetThemeItemPreview() {
    ToDoListTheme {
        SetThemeItem(
            onClick = {},
            nowTheme = AppTheme.SYSTEM
        )
    }
}