package com.arekalov.yandexshmr.presentation.home.models

sealed class HomeIntent {
    data object OnVisibleClick : HomeIntent()
    data class RemoveSwipe(val itemId: String) : HomeIntent()
    data class EditScreen(val itemId: String) : HomeIntent()
    data object ResetEditScreen : HomeIntent()
    data class OnItemCheckBoxClick(val itemId: String) : HomeIntent()

}