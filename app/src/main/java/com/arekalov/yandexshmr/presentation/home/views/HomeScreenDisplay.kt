package com.arekalov.yandexshmr.presentation.home.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.data.repository.FakeHardCodeToDoItemRepository
import com.arekalov.yandexshmr.presentation.common.navigation.NEW_ITEM
import com.arekalov.yandexshmr.presentation.common.views.CustomSnackbar
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreenDisplay(
    onItemClick: (String) -> Unit,
    navigateTOEditReset: () -> Unit,
    goEdit: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Display,
    onCheckedChange: (String) -> Unit,
    onVisibleClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onRefreshCLick: () -> Unit

) {
    val snackbarHostState = remember { SnackbarHostState() }
    val pullToRefreshState = rememberPullToRefreshState()
    LaunchedEffect(viewState) {
        if (viewState.error != null) {
            val result = snackbarHostState.showSnackbar(
                message = viewState.error.errorText,
                actionLabel = viewState.error.actionText,
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewState.error.onActionClick.invoke("")
            }
        }
    }
    if (viewState.navigateToEdit != null) {
        val destination = viewState.navigateToEdit
        navigateTOEditReset()
        goEdit(destination)
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    CustomSnackbar(
                        snackbarData = snackbarData
                    )
                }
            )
        },
        topBar = {
            AppBar(
                onVisibleClick = onVisibleClick,
                isVisibleAll = viewState.isAllVisible,
                doneCount = viewState.doneCount,
                scrollBehavior = scrollBehavior,
                onSettingsClick = onSettingsClick
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
                .padding(paddingValues)
                .padding(horizontal = 10.dp)

        ) {
            if (viewState.items.isNotEmpty()) {
                ItemsList(
                    toDoItemModels = if (viewState.isAllVisible) {
                        viewState.items
                    } else {
                        viewState.items.filter { !it.isDone }
                    },
                    onCheckedChange = { id, _ -> onCheckedChange(id) },
                    onClickItem = { id -> onItemClick(id) },
                )
            } else {
                EmptyList()
            }
            if (pullToRefreshState.isRefreshing) {
                LaunchedEffect(key1 = Unit) {
                    pullToRefreshState.startRefresh()
                    onRefreshCLick()
                    delay(200)
                    pullToRefreshState.endRefresh()
                }
            }
            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter),
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
    }

}

@Composable
fun EmptyList(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .offset(y = (-70).dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.notItemsLabel),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun EmptyListPreview() {
    ToDoListTheme {
        EmptyList()
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
                items = FakeHardCodeToDoItemRepository().itemsList,
                doneCount = FakeHardCodeToDoItemRepository().itemsList.count { it.isDone },
                isAllVisible = false,
                navigateToEdit = null,
            ),
            onCheckedChange = {},
            onVisibleClick = {},
            goEdit = {},
            navigateTOEditReset = {},
            onRefreshCLick = {},
            onSettingsClick = {}
        )
    }
}