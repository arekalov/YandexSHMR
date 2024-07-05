package com.arekalov.yandexshmr.presentation.home.models

import com.arekalov.yandexshmr.domain.model.ToDoItemModel

sealed class HomeViewState {
    data object Loading : HomeViewState()
    data class Error(val message: String) : HomeViewState()
    data class Display(
        val items: List<ToDoItemModel>,
        val doneCount: Int,
        val isAllVisible: Boolean,
        val navigateToEdit: String?
    ) : HomeViewState()

    data object Empty : HomeViewState()
}