package com.arekalov.yandexshmr.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.ToDoItemsViewModel
import com.arekalov.yandexshmr.models.Priority
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.models.ToDoItemRepository
import com.arekalov.yandexshmr.navigation.NEW_ITEM
import com.arekalov.yandexshmr.ui.ToDoListTheme


@Composable
fun Item(
    item: ToDoItem,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeBackgroundContent(dismissState: SwipeToDismissBoxState) {
    when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxSize()
                    .padding(start = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "complete icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }

        SwipeToDismissBoxValue.EndToStart -> {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .fillMaxSize()
                    .padding(end = 20.dp), contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "delete icon",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }

        SwipeToDismissBoxValue.Settled -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemWithSwipe(
    item: ToDoItem,
    modifier: Modifier = Modifier,
    onCheckChanged: (Boolean) -> Unit,
    onCheckChangedSwipe: (ToDoItem, Boolean) -> Unit,
    onClickItem: (String) -> Unit,
    onDeleteSwipe: (String) -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            return@rememberSwipeToDismissBoxState when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onCheckChangedSwipe(item, item.isDone)
                    false
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
            SwipeBackgroundContent(dismissState)
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


@Composable
fun ItemsList(
    toDoItems: List<ToDoItem>,
    onCheckedChange: (ToDoItem, Boolean) -> Unit,
    onDeleteSwipe: (String) -> Unit,
    onClickItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .padding(5.dp)
            .shadow(3.dp, shape = RoundedCornerShape(3))
    ) {
        LazyColumn {
            items(
                items = toDoItems,
                key = { it.id }
            ) { toDoItem ->
                ItemWithSwipe(
                    item = toDoItem,
                    onCheckChanged = { checked -> onCheckedChange(toDoItem, checked) },
                    onClickItem = onClickItem,
                    onCheckChangedSwipe = { item, checked -> onCheckedChange(item, checked) },
                    onDeleteSwipe = onDeleteSwipe,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onVisibleClick: () -> Unit,
    isVisibleAll: Boolean,
    doneCount: Int,
    modifier: Modifier = Modifier
) {
    val elevation = animateDpAsState(
        targetValue = if (scrollBehavior.state.collapsedFraction > 0.5) {
            20.dp
        } else {
            0.dp
        },
        label = "elevation",
        animationSpec = tween(durationMillis = 400)
    )
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp)
            .shadow(elevation.value)

    ) {
        LargeTopAppBar(
            colors = TopAppBarDefaults.largeTopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                containerColor = MaterialTheme.colorScheme.background
            ),
            title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 25.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        if (scrollBehavior.state.collapsedFraction >= 0.5) {
                            Text(
                                text = stringResource(R.string.myItemsLabel),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier
                                    .padding(start = 26.dp),
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        } else {
                            Column {
                                Text(
                                    text = stringResource(id = R.string.myItemsLabel),
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(start = 26.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.doneLabel, doneCount),
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(start = 26.dp)
                                )
                            }
                        }
                    }
                    IconButton(
                        onClick = onVisibleClick,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 15.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (isVisibleAll) {
                                    R.drawable.ic_invisible
                                } else {
                                    R.drawable.ic_visibile
                                }
                            ),
                            contentDescription = "visible icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    toDoItemsViewModel: ToDoItemsViewModel = viewModel()
) {
    val toDoItems by toDoItemsViewModel.items.collectAsState()
    val error by toDoItemsViewModel.error.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )
    var isVisibleAll by rememberSaveable {
        mutableStateOf(false)
    }
    if (error != null) {
        Toast.makeText(
            LocalContext.current,
            stringResource(
                id = R.string.errorLabel,
                error ?: stringResource(R.string.unknownErrorLabel)
            ),
            Toast.LENGTH_SHORT
        ).show()
        toDoItemsViewModel.clearError()
    }
    Scaffold(
        topBar = {
            AppBar(
                onVisibleClick = { isVisibleAll = !isVisibleAll },
                isVisibleAll = isVisibleAll,
                doneCount = toDoItems.count { it.isDone },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onItemClick(NEW_ITEM)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_plus
                    ),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "add new"
                )
            }
        }
    ) { paddingValues ->
        ItemsList(
            toDoItems = if (isVisibleAll) {
                toDoItems
            } else {
                toDoItems.filter { !it.isDone }
            },
            onCheckedChange = { item, _ -> toDoItemsViewModel.changeIsDone(item) },
            onDeleteSwipe = { id -> toDoItemsViewModel.deleteItem(id) },
            onClickItem = { id -> onItemClick(id) },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(paddingValues)
        )
    }
}

val toDoItemsList = ToDoItemRepository().itemsList

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    ToDoListTheme {
        Item(
            item = toDoItemsList[0],
            onCheckedChange = {},
            onClickEdit = {}
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ItemListPreview() {
    ToDoListTheme {
        ItemsList(
            toDoItems = toDoItemsList,
            onCheckedChange = { _, _ -> },
            onDeleteSwipe = {},
            onClickItem = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppBarPreviewUncollapsed() {
    ToDoListTheme {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
        AppBar(
            onVisibleClick = {},
            isVisibleAll = true,
            scrollBehavior = scrollBehavior,
            doneCount = toDoItemsList.count { it.isDone }
        )
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light homeScreen"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark homeScreen"
)
@Composable
private fun HomeScreenPreview() {
    ToDoListTheme {
        HomeScreen(
            onItemClick = {}
        )
    }
}