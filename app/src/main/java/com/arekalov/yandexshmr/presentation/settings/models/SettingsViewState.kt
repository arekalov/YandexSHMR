package com.arekalov.yandexshmr.presentation.settings.models

sealed class SettingsViewState {
    data class Display(
        val theme: AppTheme,
        val isAppLiked: Boolean
    ) : SettingsViewState()
}