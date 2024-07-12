package com.arekalov.yandexshmr.presentation.edit.views

import android.content.res.Configuration
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    isReadyToSave: Boolean,
    onBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    onBackButtonVisible: Boolean = true
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {},
        navigationIcon = {if (onBackButtonVisible) {
            IconButton(
                onClick = onBack,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }},
        actions = {
            TextButton(
                onClick = onSave,
                enabled = (isReadyToSave),
            ) {
                Text(
                    text = stringResource(R.string.saveLabel),
                    color = if (isReadyToSave) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary.copy(0.2f)
                    }
                )
            }
        },
        modifier = modifier
    )
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppBarPreview() {
    ToDoListTheme {
        AppBar(
            isReadyToSave = true,
            onBack = {},
            onSave = {}
        )
    }
}