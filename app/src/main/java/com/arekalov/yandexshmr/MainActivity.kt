package com.arekalov.yandexshmr

import ToDoListTheme
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.models.Priority
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.models.ToDoItemRepository


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                HomeScreen()
            }
        }
    }
}

val repo = ToDoItemRepository().todoItems

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    ItemList()
}

@Composable
fun Item(
    item: ToDoItem,
    modifier: Modifier = Modifier,
) {
    Surface(
        shadowElevation = 4.dp, tonalElevation = 0.dp,
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Checkbox(
                    checked = item.isDone,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.secondary,
                        uncheckedColor = (if (item.priority == Priority.HIGH) MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    )
                )
                if (item.priority != Priority.REGULAR && !item.isDone) {
                    Icon(
                        painter = painterResource(
                            id = if (item.priority == Priority.HIGH) R.drawable.ic_high_priority
                            else R.drawable.ic_down_arrow
                        ),
                        contentDescription = "priority",
                        tint = if (item.priority == Priority.HIGH) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(2.dp)
                    )
                }
                Text(
                    text = item.task,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (item.isDone) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = if (item.priority == Priority.REGULAR) Modifier
                        .weight(1f)
                        .padding(2.dp) else Modifier.weight(1f)
                )
                IconButton(
                    onClick = {}, modifier = Modifier
                        .size(40.dp)
                        .padding(top = 0.dp, bottom = 0.dp, end = 16.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = "info",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
            if (item.deadline != null && !item.isDone) {
                Text(
                    text = item.deadline.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.5f
                        )
                    ),
                    modifier = Modifier
                        .paddingFromBaseline(bottom = 0.dp)
                        .padding(start = 50.dp)
                )
            }
        }
    }
}

@Composable
fun ItemList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(repo) {
            Item(item = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    ToDoListTheme {
        Item(repo[0])
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemListPreview() {
    ToDoListTheme {
        ItemList()
    }
}
