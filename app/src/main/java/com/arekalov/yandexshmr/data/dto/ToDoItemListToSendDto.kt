package com.arekalov.yandexshmr.data.dto

/**
Dto for sending ToDoItemList to the server
 **/

data class ToDoItemListToSendDto(
    val list: List<ToDoItemDto>
)