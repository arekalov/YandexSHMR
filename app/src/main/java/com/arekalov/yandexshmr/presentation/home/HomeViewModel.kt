package com.arekalov.yandexshmr.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import com.arekalov.yandexshmr.presentation.home.models.HomeIntent
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ToDoItemRepository
) : ViewModel() {
    private val _homeViewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val homeViewState: StateFlow<HomeViewState>
        get() = _homeViewState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        _homeViewState.value = HomeViewState.Error(
            message = exception.message.toString()
        )
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
            is HomeIntent.OnVisibleClick -> isAllVisibleChange()
            is HomeIntent.EditScreen -> navigateToEditChange(itemId = intent.itemId)
            else -> {}
        }
    }

    private fun reduce(intent: HomeIntent, currentState: HomeViewState.Display) {
        when (intent) {
            is HomeIntent.OnVisibleClick -> isAllVisibleChange()
            is HomeIntent.RemoveSwipe -> {
                deleteItem(intent.itemId)
            }

            is HomeIntent.OnItemCheckBoxClick -> {
                changeIsDone(intent.itemId)
            }

            is HomeIntent.EditScreen -> navigateToEditChange(itemId = intent.itemId)
            is HomeIntent.ResetEditScreen -> navigateToEditChange(null)
        }
    }

    private fun isAllVisibleChange() {
        _homeViewState.value = (_homeViewState.value as HomeViewState.Display).copy(
            isAllVisible = !(_homeViewState.value as HomeViewState.Display).isAllVisible,
        )
    }

    private fun navigateToEditChange(itemId: String?) {
        _homeViewState.value = (_homeViewState.value as HomeViewState.Display).copy(
            navigateToEdit = itemId
        )
    }

    private fun deleteItem(id: String) {
        viewModelScope.launch(defaultCoroutineContext) {
            repository.deleteTodoItem(id)
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

class HomeViewModelFactory(private val repository: ToDoItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository = repository) as T
    }
}