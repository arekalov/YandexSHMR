package com.arekalov.yandexshmr

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.models.Priority
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.models.ToDoItemRepository
import com.arekalov.yandexshmr.ui.ToDoListTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ToDoItemsViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                HomeScreen(viewModel)
            }
        }
    }
}

val toDoItemsList = ToDoItemRepository().todoItems

@Composable
fun Item(
    item: ToDoItem,
    onCheckedChange: (Boolean) -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
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
            modifier = Modifier.weight(1f)
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
        IconButton(onClick = onInfoClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
        }

    }
}

@Composable
fun ItemsList(
    toDoItems: List<ToDoItem>,
    onCheckedChange: (ToDoItem, Boolean) -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .padding(3.dp)
            .shadow(3.dp, shape = RoundedCornerShape(3))
    ) {
        LazyColumn(Modifier.clip(MaterialTheme.shapes.extraLarge)) {
            items(toDoItems) { toDoItem ->
                Item(
                    item = toDoItem,
                    onCheckedChange = { checked -> onCheckedChange(toDoItem, checked) },
                    onInfoClick = onInfoClick
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
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
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
                    painter = painterResource(id = R.drawable.ic_visibile),
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    toDoItemsViewModel: ToDoItemsViewModel,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = { AppBar(scrollBehavior, {}) },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
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
            toDoItems = toDoItemsList,
            onCheckedChange = { item, _ -> toDoItemsViewModel.changeIsDone(item) },
            onInfoClick = {},
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(paddingValues)
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun ItemPreview() {
//    ToDoListTheme {
//        Item(toDoItemsList[0])
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun ItemListPreview() {
//    ToDoListTheme {
//        ItemsList(toDoItemsList)
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//private fun ToolBarPreview() {
//    ToDoListTheme {
//        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
//        AppBar(scrollBehavior = scrollBehavior)
//    }
//}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "HomeScreen Ligth")
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "HomeScreen Dark")
//@Composable
//private fun HomeScreenPreview() {
//    ToDoListTheme {
//        HomeScreen(view)
//    }
//}
