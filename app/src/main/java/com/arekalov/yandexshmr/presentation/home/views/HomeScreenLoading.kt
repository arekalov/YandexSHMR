package com.arekalov.yandexshmr.presentation.home.views


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLoading(
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Loading,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )
    Scaffold(
        topBar = {
            AppBar(
                onVisibleClick = {},
                isVisibleAll = false,
                doneCount = 0,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .offset(y = (-70).dp)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(40.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenLoadingPreview() {
    ToDoListTheme {
        HomeScreenLoading(
            viewState = HomeViewState.Loading,
        )
    }
}