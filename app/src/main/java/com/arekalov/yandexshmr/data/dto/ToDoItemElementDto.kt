package com.arekalov.yandexshmr.data.dto

/**
DTO for receiving ToDoItemElement from the server
 **/

data class ToDoItemElementDto(
    val status: String,
    val element: ToDoItemDto,
    val revision: Int
)