package com.arekalov.yandexshmr.screens

import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    var isItemNew by remember {
        mutableStateOf(false)
    }
    var item by remember {
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
            DropDownMenu(
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
                onSwitchChanged = {
                    if (it) {
                        item = item.copy(deadline = LocalDate.now())
                    } else {
                        item = item.copy(deadline = null)
                    }
                },
                onDateSelected = { newDeadline ->
                    item = item.copy(deadline = newDeadline)
                },

                modifier = Modifier
            )
            HorizontalDivider()
            TextButton(onClick = {
                if (!isItemNew) toDoItemsViewModel.deleteItem(item.id)
                onBack()
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val color = MaterialTheme.colorScheme.tertiary
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
    onSwitchChanged: (Boolean) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val mContext = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    LaunchedEffect(showDialog.value) {
        if (showDialog.value) {
            val mYear: Int
            val mMonth: Int
            val mDay: Int

            val initialDate = deadline ?: LocalDate.now()
            mYear = initialDate.year
            mMonth = initialDate.monthValue - 1
            mDay = initialDate.dayOfMonth

            android.app.DatePickerDialog(
                mContext,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
                    showDialog.value = false
                },
                mYear, mMonth, mDay
            ).show()
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.clickable { showDialog.value = true }
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
        Switch(
            checked = deadline != null,
            colors = SwitchDefaults.colors(
                uncheckedIconColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                uncheckedTrackColor = Color.White,
                uncheckedBorderColor = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            ),
            onCheckedChange = {
                onSwitchChanged(it)
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier = Modifier, onBack: () -> Unit, onSave: () -> Unit) {
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
            TextButton(onClick = onSave) {
                Text(text = stringResource(R.string.saveLabel))
            }
        },
        modifier = modifier
    )
}

@Composable
fun DropDownMenu(
    priority: Priority,
    onRegularClick: () -> Unit,
    onLowClick: () -> Unit,
    onHighClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(vertical = 10.dp)

    ) {
        TextButton(
            onClick = { expanded = true },
            contentPadding = PaddingValues(horizontal = 2.dp, vertical = 10.dp)
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
private fun DropDownMenuPreview() {
    ToDoListTheme {
        DropDownMenu(priority = Priority.LOW, {}, {}, {})
    }
}


@Preview(showBackground = true)
@Composable
fun DeadlinePickerPreview(modifier: Modifier = Modifier) {
    ToDoListTheme {
        DeadlinePicker(null, {}, {})
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    ToDoListTheme {
        AppBar(Modifier, {}, {})
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