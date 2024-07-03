package com.arekalov.yandexshmr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arekalov.yandexshmr.data.repository.ToDoItemRepositoryImpl
import com.arekalov.yandexshmr.domain.ToDoItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ToDoItemsViewModel : ViewModel() {
    private val repository = ToDoItemRepositoryImpl()

    private val _items = MutableStateFlow<List<ToDoItem>>(emptyList())
    val items: StateFlow<List<ToDoItem>>
        get() = _items.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?>
        get() = _error.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        _error.value = exception.message.toString()
    }
    private val defaultCoroutineContext = Dispatchers.IO + errorHandler

    init {
        startObservingItems()
    }

    private fun startObservingItems() {
        viewModelScope.launch(defaultCoroutineContext) {
            repository.todoItems.collect { todos ->
                _items.value = todos
            }
        }
    }

    fun addItem(item: ToDoItem) {
        viewModelScope.launch(defaultCoroutineContext) {
            repository.addTodoItem(item)
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch(defaultCoroutineContext) {
            repository.deleteTodoItem(id)
        }
    }

    fun update(id: String, item: ToDoItem) {
        viewModelScope.launch(defaultCoroutineContext) {
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
        viewModelScope.launch(defaultCoroutineContext) {
            update(id = item.id, item = item.copy(isDone = !item.isDone))
        }
    }

    fun clearError() {
        viewModelScope.launch(defaultCoroutineContext) {
            _error.value = null
        }
    }
}
