package com.arekalov.yandexshmr.data.dto

data class ToDoItemElementDto(
    val status: String,
    val element: ToDoItemDto,
    val revision: Int
)