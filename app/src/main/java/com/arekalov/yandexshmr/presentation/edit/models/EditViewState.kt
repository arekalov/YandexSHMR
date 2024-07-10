package com.arekalov.yandexshmr.presentation.edit.models

import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.presentation.common.models.Error as DataClassError

/**
All edit screen states
 **/
sealed class EditViewState {
    data class Display(
        val item: ToDoItemModel,
        val navigateToHome: Boolean,
        val error: DataClassError? = null
    ) : EditViewState()

    data class Loading(val navigateToHome: Boolean) : EditViewState()
}
