package com.arekalov.yandexshmr.presentation.edit.views

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.data.repository.FakeHardCodeToDoItemRepository
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.presentation.common.views.CustomSnackbar
import com.arekalov.yandexshmr.presentation.edit.models.EditViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme
import java.time.LocalDate


@Composable
fun EditScreenDisplay(
    viewState: EditViewState.Display,
    onBack: () -> Unit,
    onBackReset: () -> Unit,
    goHome: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onDeadlineChange: (LocalDate?) -> Unit,
    onTaskChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val placeHolderText = stringResource(R.string.taskPlaceHolder)
    LaunchedEffect(viewState) {
        if (viewState.error != null) {
            val result = snackbarHostState.showSnackbar(
                message = viewState.error.errorText,
                actionLabel = viewState.error.actionText,
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewState.error.onActionClick.invoke("")
            }
        }
    }
    if (viewState.navigateToHome) {
        onBackReset()
        goHome()
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    CustomSnackbar(
                        snackbarData = snackbarData
                    )
                }
            )
        },
        topBar = {
            AppBar(
                onBack = onBack,
                isReadyToSave = viewState.item.task.isNotEmpty(),
                onSave = onSaveClick,
            )
        },
        modifier = modifier
    ) {
        Column(
            Modifier
                .padding(20.dp)
                .verticalScroll(ScrollState(0))
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .shadow(
                        4.dp, shape = RoundedCornerShape(8)
                    )
            ) {
                TextField(
                    value = viewState.item.task,
                    onValueChange = onTaskChange,
                    minLines = 3,
                    placeholder = {
                        Text(
                            text = placeHolderText,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    modifier = Modifier
                        .semantics {
                            contentDescription =
                                if (viewState.item.task.isNotEmpty()) viewState.item.task else placeHolderText
                        }
                        .testTag(placeHolderText),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
            PriorityPicker(
                priority = viewState.item.priority,
                onPriorityChange = onPriorityChange
            )
            HorizontalDivider()
            DeadlinePicker(
                deadline = viewState.item.deadline,
                onDeadlineButtonClick = { deadline -> onDeadlineChange(deadline) },
                onRemoveDeadline = { onDeadlineChange(null) },
            )
            HorizontalDivider()
            TextButton(
                enabled = (viewState.item.task.isNotEmpty()),
                onClick = onDeleteClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val color = if (viewState.item.task.isNotEmpty()) {
                        MaterialTheme.colorScheme.tertiary
                    } else {
                        MaterialTheme.colorScheme.tertiary.copy(0.2f)
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.deleteLabel),
                        color = color,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditScreenDisplayPreview() {
    ToDoListTheme {
        EditScreenDisplay(
            viewState = EditViewState.Display(
                item = FakeHardCodeToDoItemRepository().itemsList[0],
                navigateToHome = false
            ),
            onBack = {},
            onDeadlineChange = {},
            onPriorityChange = {},
            onTaskChange = {},
            onSaveClick = {},
            onDeleteClick = {},
            goHome = {},
            onBackReset = {},
        )
    }
}