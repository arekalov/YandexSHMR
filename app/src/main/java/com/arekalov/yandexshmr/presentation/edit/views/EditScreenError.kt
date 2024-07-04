package com.arekalov.yandexshmr.presentation.edit.views


import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.presentation.edit.models.EditViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


@Composable
fun EditScreenError(
    viewState: EditViewState.Error,
    onBack: () -> Unit,
    onBackReset: () -> Unit,
    goHome: () -> Unit,
) {
    if (viewState.navigateToHome) {
        onBackReset()
        goHome()
    }
    Scaffold(
        topBar = {
            AppBar(
                onBack = onBack,
                isReadyToSave = false,
                onSave = {}
            )
        },
    ) {
        Box(
            modifier = Modifier
                .offset(y = (-70).dp)
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = viewState.message,
                modifier = Modifier
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditScreenErrorPreview() {
    ToDoListTheme {
        EditScreenError(
            viewState = EditViewState.Error(
                message = "Произошла ошибка",
                navigateToHome = false
            ),
            onBack = {},
            onBackReset = {},
            goHome = {}
        )
    }
}