package com.arekalov.yandexshmr.data.dto

data class ToDoItemListDto(
    val status: String,
    val list: List<ToDoItemDto>,
    val revision: Int
)