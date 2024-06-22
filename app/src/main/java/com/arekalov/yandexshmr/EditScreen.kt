package com.arekalov.yandexshmr

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arekalov.yandexshmr.models.Priority
import com.arekalov.yandexshmr.ui.ToDoListTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditScreen(
    id: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    toDoItemsViewModel: ToDoItemsViewModel = viewModel(),
) {
    val item = remember {
        toDoItemsViewModel.getItem(id)
    }
    println(id)
    println(item)
    Scaffold(
        topBar = {
            AppBar(
                onBack = onBack,
                onSave = { toDoItemsViewModel.update(item!!.id.value, item); onBack() })
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
                    value = item!!.task.value,
                    onValueChange = { newText: String -> item.task.value = newText },
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
                priority = item?.priority?.value ?: Priority.REGULAR,
                onRegularClick = { item?.priority?.value = Priority.REGULAR },
                onLowClick = { item?.priority?.value = Priority.LOW },
                onHighClick = { item?.priority?.value = Priority.HIGH }
            )
            HorizontalDivider()
            DeadlinePicker()
            HorizontalDivider()
            TextButton(onClick = {
                toDoItemsViewModel.deleteItem(item!!)
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
fun DeadlinePicker(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = stringResource(R.string.deadlinLabel),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,

                )
            Text(
                text = stringResource(R.string.priorityLabel),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 3.dp)
            )
        }
        Switch(
            checked = false,
            colors = SwitchDefaults.colors(),
            onCheckedChange = { })

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(modifier: Modifier = Modifier) {
    DatePickerDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = { /*TODO*/ },
        dismissButton = { /*TODO*/ }
    ) {
    }
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
        modifier = Modifier
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

@Preview
@Composable
private fun DatePickerDialogPreview() {
    ToDoListTheme {
        MyDatePickerDialog()
    }
}

@Preview(showBackground = true)
@Composable
fun DeadlinePickerPreview(modifier: Modifier = Modifier) {
    ToDoListTheme {
        DeadlinePicker()
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    ToDoListTheme {
        AppBar(Modifier, {}, {})
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditScreenPreview() {
    ToDoListTheme {
        EditScreen("id1", {})
    }
}