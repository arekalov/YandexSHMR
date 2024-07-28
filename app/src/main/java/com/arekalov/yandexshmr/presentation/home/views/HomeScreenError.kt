package com.arekalov.yandexshmr.presentation.home.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenError(
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Error,
    onSettingsClick: () -> Unit,
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
                scrollBehavior = scrollBehavior,
                onSettingsClick = onSettingsClick
            )
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .offset(y = (-70).dp)
        ) {
            Text(
                text = viewState.message,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(
                onClick = viewState.onReloadClick,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(35.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_reload),
                    contentDescription = stringResource(R.string.updateDescr),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenErrorPreview() {
    ToDoListTheme {
        HomeScreenError(
            viewState = HomeViewState.Error(
                message = "Произошла ошибка",
                onReloadClick = {},
            ),
            onSettingsClick = {}
        )
    }
}