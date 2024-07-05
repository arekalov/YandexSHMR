package com.arekalov.yandexshmr.data.mappers

import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel

fun mapToDoItemModelToListItemModel(
    toDoItems: List<ToDoItemModel>
): ToDoItemListModel {
    val itemsMap = toDoItems.associateBy { it.id }
    val doneCount = toDoItems.count { it.isDone }

    return ToDoItemListModel(
        itemsMap = itemsMap,
        items = toDoItems,
        doneCount = doneCount
    )
}
