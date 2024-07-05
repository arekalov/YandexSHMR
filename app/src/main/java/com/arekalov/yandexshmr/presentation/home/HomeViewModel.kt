package com.arekalov.yandexshmr.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import com.arekalov.yandexshmr.domain.util.Resource
import com.arekalov.yandexshmr.presentation.home.models.HomeIntent
import com.arekalov.yandexshmr.presentation.home.models.HomeViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.arekalov.yandexshmr.presentation.common.models.Error as DataClassError

class HomeViewModel(
    private val repository: ToDoItemRepository
) : ViewModel() {
    private val _homeViewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val homeViewState: StateFlow<HomeViewState>
        get() = _homeViewState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        _homeViewState.value = HomeViewState.Error(
            message = exception.message.toString(),
            onReloadClick = {}
        )
    }
    private val defaultCoroutineContext = Dispatchers.IO + errorHandler

    init {
        startObservingItems()
    }

    private fun startObservingItems() {
        viewModelScope.launch(defaultCoroutineContext) {
            _homeViewState.value = HomeViewState.Loading
            delay(300)
            repository.updateToDoItemsFlow()
            if (repository.todoItems.value is Resource.Success<*>) {
                repository.todoItems.collect { toDoItemModel ->
                    _homeViewState.value = HomeViewState.Display(
                        items = toDoItemModel.data?.items ?: emptyList(),
                        doneCount = toDoItemModel.data?.doneCount ?: 0,
                        isAllVisible = if (homeViewState.value is HomeViewState.Display) {
                            (homeViewState.value as HomeViewState.Display).isAllVisible
                        } else false,
                        navigateToEdit = if (homeViewState.value is HomeViewState.Display) {
                            (homeViewState.value as HomeViewState.Display).navigateToEdit
                        } else null
                    )
                }
            } else if (repository.todoItems.value is Resource.Error<*>) {
                repository.todoItems.collect {
                    _homeViewState.value = HomeViewState.Error(
                        message = it.message.toString(),
                        onReloadClick = { startObservingItems() }
                    )
                }
            }
        }
    }

    fun obtainIntent(intent: HomeIntent) {
        when (val currentState = _homeViewState.value) {
            is HomeViewState.Loading -> {}
            is HomeViewState.Error -> {}
            is HomeViewState.Display -> reduce(intent = intent, currentState = currentState)
        }
    }


    private fun reduce(intent: HomeIntent, currentState: HomeViewState.Display) {
        when (intent) {
            is HomeIntent.OnVisibleClick -> isAllVisibleChange()
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

    private fun update(id: String, item: ToDoItemModel) {
        viewModelScope.launch(defaultCoroutineContext) {
            val answer = repository.updateItem(id = id, item = item)
            if (answer is Resource.Error) {
                _homeViewState.value = HomeViewState.Display(
                    items = (_homeViewState.value as HomeViewState.Display).items,
                    doneCount = (_homeViewState.value as HomeViewState.Display).doneCount,
                    isAllVisible = (_homeViewState.value as HomeViewState.Display).isAllVisible,
                    navigateToEdit = (_homeViewState.value as HomeViewState.Display).navigateToEdit,
                    error = DataClassError(
                        errorText = answer.message.toString(),
                        onActionClick = { obtainIntent(HomeIntent.OnItemCheckBoxClick(id)) }
                    )
                )
            }
        }
    }


    private fun changeIsDone(id: String) {
        viewModelScope.launch(defaultCoroutineContext) {
            val item =
                (_homeViewState.value as HomeViewState.Display).items.find { it.id == id }
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