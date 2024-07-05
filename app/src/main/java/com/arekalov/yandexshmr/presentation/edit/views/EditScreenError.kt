package com.arekalov.yandexshmr.presentation.edit.views


import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.edit.models.EditViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


@Composable
fun EditScreenError(
    viewState: EditViewState.Error,
    onBack: () -> Unit,
    onBackReset: () -> Unit,
    goHome: () -> Unit,
    onReloadClick: () -> Unit
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
        Column(
            modifier = Modifier
                .offset(y = (-80).dp)
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.errorLabel, viewState.message),
                modifier = Modifier,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(
                onClick = onReloadClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_reload),
                    contentDescription = "reload",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(35.dp)
                        .padding(top = 5.dp)
                )
            }
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
            goHome = {},
            onReloadClick = {}
        )
    }
}