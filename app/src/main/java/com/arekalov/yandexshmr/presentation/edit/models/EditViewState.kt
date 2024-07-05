package com.arekalov.yandexshmr.presentation.edit.models

import com.arekalov.yandexshmr.domain.model.ToDoItemModel

sealed class EditViewState {
    data class Display(
        val item: ToDoItemModel,
        val navigateToHome: Boolean
    ) : EditViewState()

    data class Error(
        val message: String,
        val navigateToHome: Boolean
    ) : EditViewState()

    data class Loading(val navigateToHome: Boolean) : EditViewState()
}