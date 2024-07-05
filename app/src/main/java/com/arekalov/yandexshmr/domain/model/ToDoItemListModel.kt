package com.arekalov.yandexshmr.domain.model


/**
Clean ToDoItemList that go to states. Contains map for faster finding data.
 **/
data class ToDoItemListModel(
    val itemsMap: Map<String, ToDoItemModel>,
    val items: List<ToDoItemModel>,
    val doneCount: Int
)