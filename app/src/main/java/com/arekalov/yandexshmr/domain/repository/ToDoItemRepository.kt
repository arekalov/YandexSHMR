package com.arekalov.yandexshmr.domain.repository

import com.arekalov.yandexshmr.domain.ToDoItem
import kotlinx.coroutines.flow.Flow

interface ToDoItemRepository {
    val todoItems: Flow<List<ToDoItem>>
    fun addTodoItem(item: ToDoItem)
    fun updateTodoItem(id: String, itemToUpdate: ToDoItem)
    fun deleteTodoItem(id: String)
}