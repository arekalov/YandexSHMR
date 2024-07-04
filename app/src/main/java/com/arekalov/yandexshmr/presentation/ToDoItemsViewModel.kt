package com.arekalov.yandexshmr.presentation

import androidx.lifecycle.ViewModel


class ToDoItemsViewModel : ViewModel() {
//    private val repository = ToDoItemRepositoryImpl()
//
//    private val _items = MutableStateFlow<List<ToDoItemDto>>(emptyList())
//    val items: StateFlow<List<ToDoItemDto>>
//        get() = _items.asStateFlow()
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?>
//        get() = _error.asStateFlow()
//
//    private val errorHandler = CoroutineExceptionHandler { _, exception ->
//        _error.value = exception.message.toString()
//    }
//    private val defaultCoroutineContext = Dispatchers.IO + errorHandler
//
//    init {
//        startObservingItems()
//    }
//
//    private fun startObservingItems() {
//        viewModelScope.launch(defaultCoroutineContext) {
//            repository.todoItems.collect { todos ->
//                _items.value = todos
//            }
//        }
//    }
//
//    fun addItem(item: ToDoItemDto) {
//        viewModelScope.launch(defaultCoroutineContext) {
//            repository.addTodoItem(item)
//        }
//    }
//
//    fun deleteItem(id: String) {
//        viewModelScope.launch(defaultCoroutineContext) {
//            repository.deleteTodoItem(id)
//        }
//    }
//
//    fun update(id: String, item: ToDoItemDto) {
//        viewModelScope.launch(defaultCoroutineContext) {
//            repository.updateTodoItem(id, item)
//        }
//    }
//
//    fun getItem(id: String): ToDoItemDto? {
//        return _items.value.find { it.id == id }
//    }
//
//    fun isItemExists(id: String): Boolean {
//        return _items.value.find { it.id == id } != null
//    }
//
//    fun changeIsDone(item: ToDoItemDto) {
//        viewModelScope.launch(defaultCoroutineContext) {
//            update(id = item.id, item = item.copy(isDone = !item.isDone))
//        }
//    }
//
//    fun clearError() {
//        viewModelScope.launch(defaultCoroutineContext) {
//            _error.value = null
//        }
//    }
}
