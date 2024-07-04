package com.arekalov.yandexshmr.presentation.home.views

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenError(
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Error
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
    ) { Modifier.padding(it) }
    Toast.makeText(LocalContext.current, viewState.message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenErrorPreview() {
    ToDoListTheme {
        HomeScreenError(
            viewState = HomeViewState.Error(message = "Произошла ошибка"),
        )
    }
}