package com.arekalov.yandexshmr.data.network.dto

/**
DTO for receiving ToDoItem from the server
 **/
data class ToDoItemNetworkDto(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long?,
    val done: Boolean,
    val color: String?,
    val created_at: Long,
    val changed_at: Long,
    val last_updated_by: String
)