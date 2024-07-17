package com.arekalov.yandexshmr.presentation.settings.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@Composable
fun OneLineItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
    ) {
        Text(
            text = stringResource(R.string.aboutAppLabel),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AboutAppItemPreview() {
    ToDoListTheme {
        OneLineItem(
            onClick = {}
        )
    }
}