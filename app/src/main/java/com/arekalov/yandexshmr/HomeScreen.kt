package com.arekalov.yandexshmr

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arekalov.yandexshmr.models.Priority
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.models.ToDoItemRepository
import com.arekalov.yandexshmr.ui.ToDoListTheme
import java.time.LocalDate
import java.time.LocalDateTime


@Composable
fun Item(
    item: ToDoItem,
    onCheckedChange: (Boolean) -> Unit,
    onClickEdit: (ToDoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClickEdit(item) }
    ) {
        Checkbox(
            checked = item.isDone.value,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                uncheckedColor = (if (item.priority.value == Priority.HIGH) MaterialTheme.colorScheme.tertiary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            )
        )

        Icon(
            painter = painterResource(
                id = if (item.priority.value == Priority.HIGH) R.drawable.ic_high_priority
                else R.drawable.ic_down_arrow
            ),
            contentDescription = "priority",
            tint = if (item.isDone.value && item.priority.value != Priority.REGULAR) MaterialTheme.colorScheme.secondary
            else if (item.priority.value == Priority.HIGH) MaterialTheme.colorScheme.tertiary
            else if (item.priority.value == Priority.LOW) MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.5f
            ) else MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(end = 5.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            Text(
                text = item.task.value,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
            if (item.deadline.value != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.deadline.value.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.tertiary
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.tertiary
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemWithSwipe(
    item: ToDoItem,
    modifier: Modifier = Modifier,
    onCheckChanged: (Boolean) -> Unit,
    onClickItem: (ToDoItem) -> Unit,
    onDeleteSwipe: (ToDoItem) -> Unit,
) {
    val currentItem by rememberUpdatedState(item)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> onDeleteSwipe(currentItem)
                SwipeToDismissBoxValue.EndToStart -> onDeleteSwipe(currentItem)
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .5f })
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = { DismissBackground(dismissState) },
        content = {
            Item(item = item, onClickEdit = onClickItem, onCheckedChange = onCheckChanged)
        })
}


@Composable
fun ItemsList(
    toDoItems: List<ToDoItem>,
    onCheckedChange: (ToDoItem, Boolean) -> Unit,
    onDeleteSwipe: (ToDoItem) -> Unit,
    onClickItem: (ToDoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .padding(3.dp)
            .shadow(3.dp, shape = RoundedCornerShape(3))
    ) {
        LazyColumn(Modifier.clip(MaterialTheme.shapes.extraLarge)) {
            items(toDoItems, key = { it.id }) { toDoItem ->
                ItemWithSwipe(
                    item = toDoItem,
                    onCheckChanged = { checked -> onCheckedChange(toDoItem, checked) },
                    onClickItem = onClickItem,
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
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                stringResource(R.string.myTasks),
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium

            )
        },
        actions = {
            IconButton(onClick = onVisibleClick) {
                Icon(
                    painter = if (isVisibleAll) painterResource(id = R.drawable.ic_visibile)
                    else painterResource(id = R.drawable.ic_invisible),
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onItemClick: (ToDoItem) -> Unit,
    modifier: Modifier = Modifier,
    toDoItemsViewModel: ToDoItemsViewModel = viewModel()
) {
    val toDoItems by toDoItemsViewModel.items.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var isVisibleAll by rememberSaveable {
        mutableStateOf(true)
    }
    Scaffold(
        topBar = { AppBar(scrollBehavior, { isVisibleAll = !isVisibleAll }, isVisibleAll) },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val item = ToDoItem(
                        id = mutableStateOf(LocalDateTime.now().hashCode().toString()),
                        task = mutableStateOf(""),
                        priority = mutableStateOf(Priority.REGULAR),
                        isDone = mutableStateOf(false),
                        creationDate = mutableStateOf(LocalDate.now())
                    )
                    toDoItemsViewModel.addItem(item)
                    onItemClick(item)
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
            toDoItems = if (isVisibleAll) toDoItems else toDoItems.filter { !it.isDone.value },
            onCheckedChange = { item, _ -> toDoItemsViewModel.changeIsDone(item) },
            onDeleteSwipe = { item -> toDoItemsViewModel.deleteItem(item) },
            onClickItem = { item -> onItemClick(item) },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(paddingValues)
        )
    }
}

val toDoItemsList = ToDoItemRepository().itemList

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    ToDoListTheme {
        Item(toDoItemsList[0], {}, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemListPreview() {
    ToDoListTheme {
        ItemsList(toDoItemsList, { it, bool -> }, {}, {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ToolBarPreview() {
    ToDoListTheme {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        AppBar(scrollBehavior = scrollBehavior, {}, false)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ToDoListTheme {
        HomeScreen(onItemClick = {})
    }
}