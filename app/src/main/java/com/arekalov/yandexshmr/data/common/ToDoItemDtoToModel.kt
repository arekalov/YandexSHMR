package com.arekalov.yandexshmr.data.common

import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel


/**
Function for mapping simple itemList to ToDoItemModel from domain
 **/
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
