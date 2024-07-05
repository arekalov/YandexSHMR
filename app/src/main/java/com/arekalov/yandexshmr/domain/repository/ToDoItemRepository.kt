package com.arekalov.yandexshmr.domain.repository

import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.util.Resource
import kotlinx.coroutines.flow.StateFlow

/**
Repository interface describes all useful methods and properties
 **/

interface ToDoItemRepository {
    val todoItems: StateFlow<Resource<ToDoItemListModel>>
    suspend fun getToDoItemListModel(): Resource<ToDoItemListModel>
    suspend fun getOrCreateItem(id: String): Resource<ToDoItemModel>
    suspend fun getItem(id: String): Resource<ToDoItemModel>
    suspend fun deleteItem(id: String): Resource<ToDoItemModel>
    suspend fun updateItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel>
    suspend fun addItem(item: ToDoItemModel): Resource<ToDoItemModel>
    suspend fun updateToDoItemsFlow()
    suspend fun updateOrAddItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel>
    fun getEmptyToDoItemModel(): ToDoItemModel
}