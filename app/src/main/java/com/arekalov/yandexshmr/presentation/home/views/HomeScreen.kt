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
            goEdit = { itemId -> navController.navigate("$EDIT/$itemId") },
            onItemClick = { id -> homeViwModel.obtainIntent(HomeIntent.EditScreen(id)) },
            onCheckedChange = { id -> homeViwModel.obtainIntent(HomeIntent.OnItemCheckBoxClick(id)) },
            viewState = state,
            onVisibleClick = { homeViwModel.obtainIntent(HomeIntent.OnVisibleClick) },
            navigateTOEditReset = { homeViwModel.obtainIntent(HomeIntent.ResetEditScreen) },

        )

        is HomeViewState.Loading -> HomeScreenLoading(
            viewState = state,
        )

        is HomeViewState.Error -> HomeScreenError(viewState = state)
    }
}