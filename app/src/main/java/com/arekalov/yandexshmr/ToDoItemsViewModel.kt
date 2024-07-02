package com.arekalov.yandexshmr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.models.ToDoItemRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ToDoItemsViewModel : ViewModel() {
    private val repository = ToDoItemRepository()
    private val _items = MutableStateFlow<List<ToDoItem>>(emptyList())
    val items: StateFlow<List<ToDoItem>> = _items.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        _error.value = exception.message.toString()
    }

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            repository.todoItems.collect { todos ->
                _items.value = todos
            }
        }
    }

    fun addItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            repository.addTodoItem(item)
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            repository.deleteTodoItem(id)
        }
    }

    fun update(id: String, item: ToDoItem) {
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            repository.updateTodoItem(id, item)
        }
    }

    fun getItem(id: String): ToDoItem? {
        return _items.value.find { it.id == id }
    }

    fun isItemExists(id: String): Boolean {
        return _items.value.find { it.id == id } != null
    }

    fun changeIsDone(item: ToDoItem) {
        val id = item.id
        viewModelScope.launch(Dispatchers.Default + errorHandler) {
            update(id = item.id, item = item.copy(isDone = !item.isDone))
        }
    }

    fun clearError() {
        _error.value = null
    }
}
