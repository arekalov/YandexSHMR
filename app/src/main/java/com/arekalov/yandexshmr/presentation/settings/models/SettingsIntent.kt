package com.arekalov.yandexshmr.presentation.settings.models

sealed class SettingsIntent {
    data class ChangeIsAppLiked(val isAppLiked: Boolean) : SettingsIntent()
    data class ChangeTheme(val theme: AppTheme) : SettingsIntent()
}