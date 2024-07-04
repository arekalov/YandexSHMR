package com.arekalov.yandexshmr.presentation.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.data.dto.ToDoItemDto


@Composable
private fun SwipeBackgroundContent() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiary)
            .fillMaxSize()
            .padding(end = 20.dp, start = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "delete icon",
            tint = MaterialTheme.colorScheme.onTertiary
        )
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "delete icon",
            tint = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemWithSwipe(
    item: ToDoItemDto,
    modifier: Modifier = Modifier,
    onCheckChanged: (Boolean) -> Unit,
    onClickItem: (String) -> Unit,
    onDeleteSwipe: (String) -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            return@rememberSwipeToDismissBoxState when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onDeleteSwipe(item.id)
                    true
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onDeleteSwipe(item.id)
                    true
                }

                SwipeToDismissBoxValue.Settled -> false
            }
        },
        positionalThreshold = { it * 0.25F },
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = {
            SwipeBackgroundContent()
        },
        content = {
            Column {
                Item(
                    item = item,
                    onCheckedChange = onCheckChanged,
                    onClickEdit = onClickItem,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                )
            }
        },
    )
}
