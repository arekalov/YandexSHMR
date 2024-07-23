package com.arekalov.yandexshmr.presentation.aboutapp.models

sealed class AboutAppIntent {
    data class ChangeIsAppLiked(val isAppLiked: Boolean) : AboutAppIntent()
    data object UpdateTheme : AboutAppIntent()
}