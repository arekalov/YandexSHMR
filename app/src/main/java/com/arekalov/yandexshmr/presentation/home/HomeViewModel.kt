package com.arekalov.yandexshmr.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.data.repository.ToDoItemRepositoryImpl
import com.arekalov.yandexshmr.presentation.home.models.HomeIntent
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = ToDoItemRepositoryImpl()

    private val _homeViewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val homeViewState: StateFlow<HomeViewState>
        get() = _homeViewState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
//        _error.value = exception.message.toString()
    }
    private val defaultCoroutineContext = Dispatchers.IO + errorHandler

    init {
        startObservingItems()
    }

    private fun startObservingItems() {
        viewModelScope.launch(defaultCoroutineContext) {
            repository.todoItems.collect { toDoItemModel ->
                _homeViewState.value = HomeViewState.Display(
                    items = toDoItemModel.items,
                    doneCount = toDoItemModel.doneCount,
                    isAllVisible = if (homeViewState.value is HomeViewState.Display) {
                        (homeViewState.value as HomeViewState.Display).isAllVisible
                    } else false,
                    navigateToEdit = if (homeViewState.value is HomeViewState.Display) {
                        (homeViewState.value as HomeViewState.Display).navigateToEdit
                    } else null
                )
            }
        }
    }

    fun obtainIntent(intent: HomeIntent) {
        when (val currentState = _homeViewState.value) {
            is HomeViewState.Loading -> {}
            is HomeViewState.Empty -> reduce(intent = intent, currentState = currentState)
            is HomeViewState.Error -> {}
            is HomeViewState.Display -> reduce(intent = intent, currentState = currentState)
        }
    }

    private fun reduce(intent: HomeIntent, currentState: HomeViewState.Empty) {
        when (intent) {
            is HomeIntent.VisibleClick -> isAllVisibleChange()

            is HomeIntent.EditScreen -> navigateToEditChange(itemId = intent.itemId)

            else -> {}
        }
    }

    private fun reduce(intent: HomeIntent, currentState: HomeViewState.Display) {
        when (intent) {
            is HomeIntent.VisibleClick -> isAllVisibleChange()

            is HomeIntent.RemoveSwipe -> {
                deleteItem(intent.itemId)
            }

            is HomeIntent.OnItemCheckBoxClick -> {
                changeIsDone(intent.itemId)
            }

            is HomeIntent.EditScreen -> navigateToEditChange(itemId = intent.itemId)
        }
    }

    private fun isAllVisibleChange() {
        _homeViewState.value = (_homeViewState.value as HomeViewState.Display).copy(
            isAllVisible = !(_homeViewState.value as HomeViewState.Display).isAllVisible,
        )
    }

    private fun navigateToEditChange(itemId: String) {
        _homeViewState.value = (_homeViewState.value as HomeViewState.Display).copy(
            navigateToEdit = itemId
        )
    }

    private fun deleteItem(id: String) {
        viewModelScope.launch(defaultCoroutineContext) {
            Log.e("!!!", (homeViewState.value as HomeViewState.Display).items.count().toString())
            repository.deleteTodoItem(id)
            delay(100)
            Log.e("!!!", (homeViewState.value as HomeViewState.Display).items.count().toString())
        }
    }

    private fun update(id: String, item: ToDoItemDto) {
        viewModelScope.launch(defaultCoroutineContext) {
            repository.updateTodoItem(id, item)
        }
    }

    private fun changeIsDone(id: String) {
        viewModelScope.launch(defaultCoroutineContext) {
            val item = (_homeViewState.value as HomeViewState.Display).items.find { it.id == id }
            if (item != null) {
                update(id = id, item = item.copy(isDone = !item.isDone))
            }
        }
    }
}