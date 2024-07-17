package com.arekalov.yandexshmr.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arekalov.yandexshmr.presentation.common.navigation.EDIT
import com.arekalov.yandexshmr.presentation.common.navigation.SETTINGS
import com.arekalov.yandexshmr.presentation.home.models.HomeIntent
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import com.arekalov.yandexshmr.presentation.home.views.HomeScreenDisplay
import com.arekalov.yandexshmr.presentation.home.views.HomeScreenError
import com.arekalov.yandexshmr.presentation.home.views.HomeScreenLoading

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
            onRefreshCLick = { homeViwModel.obtainIntent(HomeIntent.Refresh) },
            onSettingsClick = { navController.navigate(SETTINGS) }
        )

        is HomeViewState.Loading -> HomeScreenLoading(
            viewState = state,
            onSettingsCLick = { navController.navigate(SETTINGS) }
        )

        is HomeViewState.Error -> HomeScreenError(
            viewState = state,
            onSettingsClick = { navController.navigate(SETTINGS) }
        )
    }
}