package com.arekalov.yandexshmr.data.network.dto

/**
Dto for sending ToDoItemList to the server
 **/

data class ToDoItemListToSendNetworkDto(
    val list: List<ToDoItemNetworkDto>
)