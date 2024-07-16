package com.arekalov.yandexshmr.data.network.dto

/**
DTO for receiving ToDoItemElement from the server
 **/

data class ToDoItemElementNetworkDto(
    val status: String,
    val element: ToDoItemNetworkDto,
    val revision: Int
)