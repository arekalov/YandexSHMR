package com.arekalov.yandexshmr.presentation.edit.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


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
            contentPadding = PaddingValues(
                horizontal = 4.dp,
                vertical = 10.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
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
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
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
                onClick = {
                    onRegularClick()
                    expanded = false
                }
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
                onClick = {
                    onLowClick()
                    expanded = false
                }
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
                onClick = {
                    onHighClick()
                    expanded = false
                }
            )
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PriorityPickerPreview() {
    ToDoListTheme {
        PriorityPicker(
            priority = Priority.LOW,
            onRegularClick = {},
            onLowClick = {},
            onHighClick = {}
        )
    }
}
