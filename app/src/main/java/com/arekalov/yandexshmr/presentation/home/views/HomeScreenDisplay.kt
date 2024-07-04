package com.arekalov.yandexshmr.presentation.home.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.data.repository.ToDoItemRepositoryImpl
import com.arekalov.yandexshmr.presentation.common.navigation.NEW_ITEM
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenDisplay(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Display,
    onCheckedChange: (String) -> Unit,
    onDeleteSwipe: (String) -> Unit,
    onVisibleClick: () -> Unit

) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )
    Scaffold(
        topBar = {
            AppBar(
                onVisibleClick = onVisibleClick,
                isVisibleAll = viewState.isAllVisible,
                doneCount = viewState.doneCount,
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
            toDoItemDtos = if (viewState.isAllVisible) {
                viewState.items
            } else {
                viewState.items.filter { !it.isDone }
            },
            onCheckedChange = { id, _ -> onCheckedChange(id) },
            onDeleteSwipe = onDeleteSwipe,
            onClickItem = { id -> onItemClick(id) },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(paddingValues)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenDisplayPreview() {
    ToDoListTheme {
        HomeScreenDisplay(
            onItemClick = {},
            viewState = HomeViewState.Display(
                items = ToDoItemRepositoryImpl().itemsList,
                doneCount = ToDoItemRepositoryImpl().itemsList.count { it.isDone },
                isAllVisible = false,
                navigateToEdit = null
            ),
            onCheckedChange = {},
            onDeleteSwipe = {},
            onVisibleClick = {}
        )
    }
}