package com.arekalov.yandexshmr.domain.repository

import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import kotlinx.coroutines.flow.Flow

interface ToDoItemRepository {
    val todoItems: Flow<ToDoItemModel>
    fun addTodoItem(item: ToDoItemDto)
    fun updateTodoItem(id: String, itemToUpdate: ToDoItemDto)
    fun deleteTodoItem(id: String)
}