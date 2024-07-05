package com.arekalov.yandexshmr.domain.model

data class ToDoItemListModel(
    val itemsMap: Map<String, ToDoItemModel>,
    val items: List<ToDoItemModel>,
    val doneCount: Int
)