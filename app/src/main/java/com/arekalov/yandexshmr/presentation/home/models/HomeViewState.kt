package com.arekalov.yandexshmr.presentation.home.models

import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.presentation.common.models.Error as DataClassError

/**
All homeScreen states
 **/

sealed class HomeViewState {
    data object Loading : HomeViewState()
    data class Error(
        val message: String,
        val onReloadClick: () -> Unit,
    ) : HomeViewState()

    data class Display(
        val items: List<ToDoItemModel>,
        val doneCount: Int,
        val isAllVisible: Boolean,
        val navigateToEdit: String?,
        val error: DataClassError? = null
    ) : HomeViewState()

}