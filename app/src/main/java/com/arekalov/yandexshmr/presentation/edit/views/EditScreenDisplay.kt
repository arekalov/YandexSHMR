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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.data.dto.Priority
import com.arekalov.yandexshmr.data.repository.ToDoItemRepositoryImpl
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
    if (viewState.navigateToHome) {
        onBackReset()
        goHome()
    }
    Scaffold(
        topBar = {
            AppBar(
                onBack = onBack,
                isReadyToSave = viewState.item.task.isNotEmpty(),
                onSave = onSaveClick
            )
        },
    ) {
        Column(
            Modifier
                .padding(20.dp)
                .verticalScroll(ScrollState(0))
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = modifier
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
                            text = stringResource(R.string.taskPlaceHolder),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
            PriorityPicker(
                priority = viewState.item.priority,
                onRegularClick = { onPriorityChange(Priority.REGULAR) },
                onLowClick = { onPriorityChange(Priority.LOW) },
                onHighClick = { onPriorityChange(Priority.HIGH) }
            )
            HorizontalDivider()
            DeadlinePicker(
                deadline = viewState.item.deadline,
                onDeadlineButtonClick = { deadline -> onDeadlineChange(deadline) },
                onRemoveDeadline = { onDeadlineChange(null) },
                modifier = Modifier
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
                        contentDescription = "delete",
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
                item = ToDoItemRepositoryImpl().itemsList[0],
                navigateToHome = false
            ),
            onBack = {},
            onDeadlineChange = {},
            onPriorityChange = {},
            onTaskChange = {},
            onSaveClick = {},
            onDeleteClick = {},
            goHome = {},
            onBackReset = {}
        )
    }
}