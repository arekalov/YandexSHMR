package com.arekalov.yandexshmr.presentation.home.views

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.data.dto.Priority
import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.data.repository.ToDoItemRepositoryImpl
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


@Composable
fun Item(
    item: ToDoItemDto,
    onCheckedChange: (Boolean) -> Unit,
    onClickEdit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClickEdit(item.id) }
    ) {
        Checkbox(
            checked = item.isDone,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                uncheckedColor = if (item.priority == Priority.HIGH) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                }

            )
        )

        Icon(
            painter = painterResource(
                id = if (item.priority == Priority.HIGH) {
                    R.drawable.ic_high_priority
                } else {
                    R.drawable.ic_down_arrow
                }
            ),
            contentDescription = "priority",
            tint = when {
                item.isDone && item.priority != Priority.REGULAR -> MaterialTheme.colorScheme.secondary
                item.priority == Priority.HIGH -> MaterialTheme.colorScheme.tertiary
                item.priority == Priority.LOW -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                else -> Color.Transparent
            },
            modifier = Modifier
                .padding(end = 10.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            Text(
                text = item.task,
                style = MaterialTheme.typography.bodyMedium,
                color = if (item.isDone) {
                    MaterialTheme.colorScheme.onSurface.copy(0.7f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                textDecoration = if (item.isDone) {
                    TextDecoration.LineThrough
                } else {
                    null
                },
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
            if (item.deadline != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.deadline.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ItemPreview() {
    ToDoListTheme {
        Item(
            item = ToDoItemRepositoryImpl().itemsList[0],
            onCheckedChange = {},
            onClickEdit = {}
        )
    }
}