package com.arekalov.yandexshmr.domain.repository

import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import kotlinx.coroutines.flow.Flow

interface ToDoItemRepository {
    val todoItems: Flow<ToDoItemModel>
    suspend fun addTodoItem(item: ToDoItemDto)
    suspend fun updateTodoItem(id: String, itemToUpdate: ToDoItemDto)
    suspend fun deleteTodoItem(id: String)
    suspend fun getOrCreateItem(id: String): ToDoItemDto
}