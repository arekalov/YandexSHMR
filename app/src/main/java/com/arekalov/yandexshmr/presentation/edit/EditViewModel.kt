package com.arekalov.yandexshmr.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.arekalov.yandexshmr.data.dto.Priority
import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import com.arekalov.yandexshmr.presentation.edit.models.EditIntent
import com.arekalov.yandexshmr.presentation.edit.models.EditViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class EditViewModel(
    private val repository: ToDoItemRepository
) : ViewModel() {
    private val _editViewState = MutableStateFlow<EditViewState>(EditViewState.Loading(false))
    val editViewState: StateFlow<EditViewState>
        get() = _editViewState
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        _editViewState.value = EditViewState.Error(
            navigateToHome = false,
            message = "Ошибка: ${exception.message.toString()}"
        )
    }
    private val defaultCoroutineContext = Dispatchers.IO + errorHandler

    fun obtainIntent(intent: EditIntent) {
        when (val currentState = _editViewState.value) {
            is EditViewState.Loading -> reduce(intent = intent, currentState = currentState)
            is EditViewState.Error -> reduce(intent = intent, currentState = currentState)
            is EditViewState.Display -> reduce(intent = intent, currentState = currentState)
        }
    }

    private fun reduce(intent: EditIntent, currentState: EditViewState.Display) {
        when (intent) {
            is EditIntent.InitState -> initState(intent.itemId)
            is EditIntent.BackToHome -> navigateToHomeChange()
            is EditIntent.OnSaveCLick -> {
                update(
                    id = currentState.item.id,
                    item = currentState.item
                )
                navigateToHomeChange()
            }

            is EditIntent.OnDeleteClick -> {
                deleteItem(id = currentState.item.id)
                navigateToHomeChange()
            }

            is EditIntent.ItemTaskEdit -> editItemTask(intent.task)
            is EditIntent.ItemDeadlineEdit -> editItemDeadline(intent.deadline)
            is EditIntent.ItemPriorityEdit -> editItemPriority(intent.priority)
            is EditIntent.ResetBackToHome -> navigateToHomeChange()
        }
    }

    private fun reduce(intent: EditIntent, currentState: EditViewState.Error) {
        when (intent) {
            is EditIntent.BackToHome -> navigateToHomeChange()
            else -> {}
        }
    }

    private fun reduce(intent: EditIntent, currentState: EditViewState.Loading) {
        when (intent) {
            is EditIntent.InitState -> initState(intent.itemId)
            is EditIntent.BackToHome -> navigateToHomeChange()
            is EditIntent.ResetBackToHome -> navigateToHomeChange()
            else -> {}
        }
    }

    private fun editItemDeadline(deadline: LocalDate?) {
        _editViewState.value = (_editViewState.value as EditViewState.Display).copy(
            item = (_editViewState.value as EditViewState.Display).item.copy(
                deadline = deadline
            )
        )
    }

    private fun editItemPriority(priority: Priority) {
        _editViewState.value = (_editViewState.value as EditViewState.Display).copy(
            item = (_editViewState.value as EditViewState.Display).item.copy(
                priority = priority
            )
        )
    }

    private fun editItemTask(task: String) {
        _editViewState.value = (_editViewState.value as EditViewState.Display).copy(
            item = (_editViewState.value as EditViewState.Display).item.copy(
                task = task
            )
        )
    }


    private fun navigateToHomeChange() {
        when (val currentState = _editViewState.value) {
            is EditViewState.Loading -> _editViewState.value = currentState.copy(
                navigateToHome = !currentState.navigateToHome
            )

            is EditViewState.Display -> _editViewState.value = currentState.copy(
                navigateToHome = !currentState.navigateToHome
            )

            is EditViewState.Error -> _editViewState.value = currentState.copy(
                navigateToHome = !currentState.navigateToHome
            )
        }
    }

    private fun initState(id: String) {
        viewModelScope.launch(defaultCoroutineContext) {
            val item = async {
                repository.getOrCreateItem(id)
            }.await()
            _editViewState.value = EditViewState.Display(
                item = item,
                navigateToHome = false
            )
        }
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
}

class EditViewModelFactory(private val repository: ToDoItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditViewModel(repository = repository) as T
    }
}