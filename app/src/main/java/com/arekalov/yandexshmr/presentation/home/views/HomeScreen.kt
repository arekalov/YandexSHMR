package com.arekalov.yandexshmr.presentation.home.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arekalov.yandexshmr.presentation.common.navigation.EDIT
import com.arekalov.yandexshmr.presentation.home.HomeViewModel
import com.arekalov.yandexshmr.presentation.home.models.HomeIntent
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState

@Composable
fun HomeScreen(
    navController: NavController,
    homeViwModel: HomeViewModel = viewModel()
) {
    val viewState = homeViwModel.homeViewState.collectAsState()
    when (val state = viewState.value) {
        is HomeViewState.Display -> HomeScreenDisplay(
            onItemClick = { navController.navigate(EDIT) },
            onDeleteSwipe = { id -> homeViwModel.obtainIntent(HomeIntent.RemoveSwipe(id)) },
            onCheckedChange = { id -> homeViwModel.obtainIntent(HomeIntent.OnItemCheckBoxClick(id)) },
            viewState = state,
            onVisibleClick = { homeViwModel.obtainIntent(HomeIntent.VisibleClick) }
        )

        is HomeViewState.Loading -> HomeScreenLoading(
            viewState = state,
        )

        is HomeViewState.Empty -> HomeScreenEmpty(
            viewState = state,
            onItemClick = { navController.navigate(EDIT) }
        )

        is HomeViewState.Error -> HomeScreenError(viewState = state)
    }
}