package com.arekalov.yandexshmr.domain.model

import com.arekalov.yandexshmr.data.dto.ToDoItemDto

data class ToDoItemModel(
    val itemsMap: Map<String, ToDoItemDto>,
    val items: List<ToDoItemDto>,
    val doneCount: Int
)