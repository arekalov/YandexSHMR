package com.arekalov.yandexshmr

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.models.ToDoItemRepository

class ToDoItemsViewModel() : ViewModel() {
    private val repository = ToDoItemRepository()
    private var _items = repository.todoItems.toMutableStateList()
    val items: List<ToDoItem>
        get() = _items

    fun changeIsDone(item: ToDoItem) {
        item.isDone.value = !item.isDone.value
    }

    private fun updateItem(id: String, item: ToDoItem) {
        repository.updateItem(id, item)
    }

    fun removeTask(item: ToDoItem) {
        repository.removeItem(item)
    }
}
