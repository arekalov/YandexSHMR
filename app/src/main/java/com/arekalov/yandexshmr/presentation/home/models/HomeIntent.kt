package com.arekalov.yandexshmr.presentation.home.models

/**
All homeScreen intents
 **/
sealed class HomeIntent {
    data object OnVisibleClick : HomeIntent()
    data class EditScreen(val itemId: String) : HomeIntent()
    data object ResetEditScreen : HomeIntent()
    data class OnItemCheckBoxClick(val itemId: String) : HomeIntent()

}