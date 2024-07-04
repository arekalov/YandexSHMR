package com.arekalov.yandexshmr.data.mappers

import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun mapToDoItemsToModel(
    toDoItems: List<ToDoItemDto>
): ToDoItemModel {
    val itemsMap = toDoItems.associateBy { it.id }
    val doneCount = toDoItems.count { it.isDone }

    return ToDoItemModel(
        itemsMap = itemsMap,
        items = toDoItems,
        doneCount = doneCount
    )
}

fun Flow<List<ToDoItemDto>>.toToDoItemModel(): Flow<ToDoItemModel> {
    return this.map { toDoItems ->
        mapToDoItemsToModel(toDoItems)
    }
}
