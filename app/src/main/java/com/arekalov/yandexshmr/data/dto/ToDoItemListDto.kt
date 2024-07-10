package com.arekalov.yandexshmr.data.dto

/**
DTO for receiving ToDoItemList from the server
 **/
data class ToDoItemListDto(
    val status: String,
    val list: List<ToDoItemDto>,
    val revision: Int
)