package com.arekalov.yandexshmr.data.network.dto

/**
DTO for receiving ToDoItemList from the server
 **/
data class ToDoItemListNetworkDto(
    val status: String,
    val list: List<ToDoItemNetworkDto>,
    val revision: Int
)