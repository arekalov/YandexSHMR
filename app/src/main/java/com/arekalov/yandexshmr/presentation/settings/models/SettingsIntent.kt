package com.arekalov.yandexshmr.presentation.settings.models

import com.arekalov.yandexshmr.presentation.common.models.AppTheme

sealed class SettingsIntent {
    data class ChangeTheme(val theme: AppTheme) : SettingsIntent()
}