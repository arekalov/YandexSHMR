package com.arekalov.yandexshmr.presentation.edit.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlinePicker(
    deadline: LocalDate?,
    onDeadlineButtonClick: (LocalDate?) -> Unit,
    onRemoveDeadline: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    TextButton(
        onClick = {
            showDialog = !showDialog
        },
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 10.dp),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        if (showDialog) {
            val datePickerState = rememberDatePickerState(
                deadline?.atStartOfDay()?.toInstant(ZoneOffset.MIN)?.toEpochMilli()
            )
            DatePickerDialog(
                tonalElevation = 300.dp,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background.copy()
                ),
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val localDate =
                                Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            onDeadlineButtonClick(localDate)
                            showDialog = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.okLabel))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = stringResource(id = R.string.cancelLabel))
                    }
                }
            ) {
                DatePicker(
                    colors = DatePickerDefaults.colors(
                        headlineContentColor = MaterialTheme.colorScheme.onSurface,
                        titleContentColor = MaterialTheme.colorScheme.primary.copy(0.8f),
                        navigationContentColor = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                    ),
                    state = datePickerState
                )

            }
        }
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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