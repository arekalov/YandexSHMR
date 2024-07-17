package com.arekalov.yandexshmr.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arekalov.yandexshmr.presentation.settings.models.SettingsViewState
import com.arekalov.yandexshmr.presentation.settings.views.SettingsScreenDisplay

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val viewState = settingsViewModel.settingsState.collectAsState()
    when (val state = viewState.value) {
        is SettingsViewState.Display -> SettingsScreenDisplay(
            onBackClicked = { navController.popBackStack() }
        )
    }
}