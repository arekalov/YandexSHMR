package com.arekalov.yandexshmr.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arekalov.yandexshmr.presentation.aboutapp.AboutAppViewModel
import com.arekalov.yandexshmr.presentation.aboutapp.models.AboutAppIntent
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.arekalov.yandexshmr.presentation.common.navigation.ABOUT
import com.arekalov.yandexshmr.presentation.settings.models.SettingsIntent
import com.arekalov.yandexshmr.presentation.settings.views.SettingsScreenDisplay

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel(),
    aboutAppViewModel: AboutAppViewModel = viewModel()
) {
    val viewState = settingsViewModel.settingsState.collectAsState()
    SettingsScreenDisplay(
        onBackClicked = { navController.popBackStack() },
        viewState = viewState.value,
        onThemeChanged = { theme: AppTheme ->
            settingsViewModel.obtainIntent(
                intent = SettingsIntent.ChangeTheme(
                    theme
                )
            )
            aboutAppViewModel.obtainIntent(AboutAppIntent.UpdateTheme)
        },
        onAboutAppClick = { navController.navigate(ABOUT) }
    )
}