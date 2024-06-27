package com.arekalov.yandexshmr.screens

import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.ToDoItemsViewModel
import com.arekalov.yandexshmr.models.Priority
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.navigation.NEW_ITEM
import com.arekalov.yandexshmr.ui.ToDoListTheme
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun EditScreen(
    id: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    toDoItemsViewModel: ToDoItemsViewModel = viewModel(),
) {
    var isItemNew by rememberSaveable {
        mutableStateOf(false)
    }
    var item by rememberSaveable {
        mutableStateOf(
            if (id != NEW_ITEM && toDoItemsViewModel.isItemExists(id)) {
                toDoItemsViewModel.getItem(id = id)!!
            } else {
                isItemNew = true
                val tempId = LocalDateTime.now().hashCode()
                    .toString() // Временное решение для создания id, потом заменится на primaryKey из DB
                ToDoItem(
                    id = tempId,
                    task = "",
                    priority = Priority.REGULAR,
                    isDone = false,
                    creationDate = LocalDate.now()
                )
            }
        )
    }
    Scaffold(
        topBar = {
            AppBar(
                onBack = onBack,
                isReadyToSave = item.task.isNotEmpty(),
                onSave = {
                    if (isItemNew) {
                        toDoItemsViewModel.addItem(item = item)
                    } else {
                        toDoItemsViewModel.update(item.id, item)
                    }
                    onBack()
                })
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
                    value = item.task,
                    onValueChange = { newText: String ->
                        item = item.copy(task = newText)
                    },
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
                priority = item.priority,
                onRegularClick = {
                    item = item.copy(priority = Priority.REGULAR)
                },
                onLowClick = {
                    item = item.copy(priority = Priority.LOW)
                },
                onHighClick = {
                    item = item.copy(priority = Priority.HIGH)
                }
            )
            HorizontalDivider()
            DeadlinePicker(
                deadline = item.deadline,
                onDeadlineButtonClick = { newDeadline ->
                    item = item.copy(deadline = newDeadline)
                },
                onRemoveDeadline = { item = item.copy(deadline = null) },
                modifier = Modifier
            )
            HorizontalDivider()
            TextButton(
                enabled = (item.task.isNotEmpty()),
                onClick = {
                    if (!isItemNew) toDoItemsViewModel.deleteItem(item.id)
                    onBack()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val color = if (item.task.isNotEmpty()) {
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

@Composable
fun DeadlinePicker(
    deadline: LocalDate?,
    onDeadlineButtonClick: (LocalDate?) -> Unit,
    onRemoveDeadline: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    TextButton(
        onClick = {
            val year: Int
            val month: Int
            val day: Int
            val initialDate = deadline ?: LocalDate.now()
            year = initialDate.year
            month = initialDate.monthValue - 1
            day = initialDate.dayOfMonth
            android.app.DatePickerDialog(
                context,
                { _: DatePicker, takenYear: Int, takenMonth: Int, takenDayOfMonth: Int ->
                    onDeadlineButtonClick(LocalDate.of(takenYear, takenMonth + 1, takenDayOfMonth))

                },
                year, month, day
            ).show()
        },
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 10.dp),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(R.string.deadlinLabel),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = deadline?.toString() ?: stringResource(R.string.noDeadlineLabel),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
            IconButton(
                onClick = onRemoveDeadline,
                enabled = deadline != null
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    tint = if (deadline != null) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    contentDescription = "remove deadline"
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    isReadyToSave: Boolean,
    onBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
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

@Composable
fun PriorityPicker(
    priority: Priority,
    onRegularClick: () -> Unit,
    onLowClick: () -> Unit,
    onHighClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(vertical = 10.dp)

    ) {
        TextButton(
            onClick = { expanded = true },
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 10.dp)
        ) {
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.priorityLabel),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,

                    )
                Text(
                    text = when (priority) {
                        Priority.HIGH -> stringResource(id = R.string.highPriorityLabel)
                        Priority.REGULAR -> stringResource(id = R.string.reqularPriorityLabel)
                        Priority.LOW -> stringResource(id = R.string.lowPriorityLabel)
                    },
                    color = when (priority) {
                        Priority.HIGH -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
                        else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        offset = DpOffset(15.dp, 0.dp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .width(150.dp)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.reqularPriorityLabel),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(10.dp)
                )
            },
            onClick = { onRegularClick(); expanded = false }
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.lowPriorityLabel),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(10.dp)
                )
            },
            onClick = { onLowClick(); expanded = false }
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.highPriorityLabel),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(10.dp)
                )
            },
            onClick = { onHighClick(); expanded = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PriorityPickerPreview() {
    ToDoListTheme {
        PriorityPicker(priority = Priority.LOW, {}, {}, {})
    }
}


@Preview(showBackground = true)
@Composable
fun DeadlinePickerPreview(modifier: Modifier = Modifier) {
    ToDoListTheme {
        DeadlinePicker(
            deadline = LocalDate.of(2024, 9, 1),
            onDeadlineButtonClick = {},
            onRemoveDeadline = {}
        )
    }
}

@Preview
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


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditScreenPreview() {
    ToDoListTheme {
        EditScreen("id1", {})
    }
}