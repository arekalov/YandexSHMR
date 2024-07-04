package com.arekalov.yandexshmr.presentation.edit.models

import com.arekalov.yandexshmr.data.dto.ToDoItemDto

sealed class EditViewState {
    data class Display(
        val item: ToDoItemDto,
        val navigateToHome: Boolean
    ) : EditViewState()

    data class Error(
        val message: String,
        val navigateToHome: Boolean
    ) : EditViewState()

    data class Loading(val navigateToHome: Boolean) : EditViewState()
}