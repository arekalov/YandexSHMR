package com.arekalov.yandexshmr.presentation.home.models

sealed class HomeIntent {
    data object VisibleClick : HomeIntent()
    data class RemoveSwipe(val itemId: String) : HomeIntent()
    data class EditScreen(val itemId: String) : HomeIntent()
    data class OnItemCheckBoxClick(val itemId: String) : HomeIntent()
}