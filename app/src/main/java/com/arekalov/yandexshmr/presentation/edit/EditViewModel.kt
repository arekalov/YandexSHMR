package com.arekalov.yandexshmr.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import com.arekalov.yandexshmr.domain.util.Resource
import com.arekalov.yandexshmr.presentation.edit.models.EditIntent
import com.arekalov.yandexshmr.presentation.edit.models.EditViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import com.arekalov.yandexshmr.presentation.common.models.Error as ErrorDataClass

/**
ViewModel, that content editScreen state and work with it.
 **/

@HiltViewModel
class EditViewModel @Inject constructor(
    private val repository: ToDoItemRepository
) : ViewModel() {
    private val _editViewState = MutableStateFlow<EditViewState>(EditViewState.Loading(false))
    val editViewState: StateFlow<EditViewState>
        get() = _editViewState
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        when (val currentState = _editViewState.value) {
            is EditViewState.Display -> {
                _editViewState.value = EditViewState.Display(
                    item = currentState.item,
                    error = (
                            ErrorDataClass(
                                errorText = exception.message.toString(),
                                onActionClick = {},
                                actionText = null
                            )
                            ),
                    navigateToHome = false
                )
            }

            else -> {
                _editViewState.value = EditViewState.Display(
                    item = repository.getEmptyToDoItemModel(),
                    error = ErrorDataClass(
                        errorText = exception.message.toString(),
                        onActionClick = {},
                        actionText = null
                    ),
                    navigateToHome = false
                )
            }
        }
    }
    private val defaultCoroutineContext = Dispatchers.IO + errorHandler

    init {
        _editViewState.value = EditViewState.Loading(navigateToHome = false)
    }

    fun obtainIntent(intent: EditIntent) {
        when (val currentState = _editViewState.value) {
            is EditViewState.Loading -> reduce(intent = intent, currentState = currentState)
            is EditViewState.Display -> reduce(intent = intent, currentState = currentState)
        }
    }

    private fun reduce(intent: EditIntent, currentState: EditViewState.Display) {
        when (intent) {
            is EditIntent.InitState -> {
                initState(intent.itemId)
            }

            is EditIntent.BackToHome -> backToHome()
            is EditIntent.OnSaveCLick -> update(
                id = currentState.item.id,
                item = currentState.item
            )

            is EditIntent.OnDeleteClick -> deleteItem(id = currentState.item.id)

            is EditIntent.ItemTaskEdit -> editItemTask(intent.task)
            is EditIntent.ItemDeadlineEdit -> editItemDeadline(intent.deadline)
            is EditIntent.ItemPriorityEdit -> editItemPriority(intent.priority)
            is EditIntent.ResetBackToHome -> navigateToHomeChange()
        }
    }


    private fun reduce(intent: EditIntent, currentState: EditViewState.Loading) {
        when (intent) {
            is EditIntent.InitState -> {
                initState(intent.itemId)
            }

            is EditIntent.BackToHome -> backToHome()
            is EditIntent.ResetBackToHome -> navigateToHomeChange()
            else -> {}
        }
    }

    private fun backToHome() {
        _editViewState.value = EditViewState.Loading(true)
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

        }
    }

    private fun initState(id: String) {
        viewModelScope.launch(defaultCoroutineContext) {
            delay(300)
            val item = async {
                repository.getOrCreateItem(id = id)
            }.await()
            if (item is Resource.Success) {
                _editViewState.value = EditViewState.Display(
                    item = item.data!!,
                    navigateToHome = false
                )
            } else {
                _editViewState.value = EditViewState.Display(
                    item = repository.getEmptyToDoItemModel(),
                    error = ErrorDataClass(
                        errorText = item.message.toString(),
                        onActionClick = { id: String -> obtainIntent(EditIntent.InitState(id)) }
                    ),
                    navigateToHome = false
                )
            }
        }
    }

    private fun deleteItem(id: String) {
        viewModelScope.launch(defaultCoroutineContext) {
            val answer = repository.deleteItem(id)
            if (answer is Resource.Error) {
                _editViewState.value = EditViewState.Display(
                    item = (_editViewState.value as EditViewState.Display).item,
                    navigateToHome = false,
                    error = ErrorDataClass(
                        errorText = answer.message.toString(),
                        onActionClick = { obtainIntent(EditIntent.OnDeleteClick) }
                    )
                )
            } else {
                backToHome()
            }
        }
    }

    private fun update(id: String, item: ToDoItemModel) {
        viewModelScope.launch(defaultCoroutineContext) {
            val answer = repository.updateOrAddItem(id = id, item = item)
            if (answer is Resource.Error) {
                _editViewState.value = EditViewState.Display(
                    item = (_editViewState.value as EditViewState.Display).item,
                    navigateToHome = false,
                    error = ErrorDataClass(
                        errorText = answer.message.toString(),
                        onActionClick = { obtainIntent(EditIntent.OnSaveCLick) }
                    )
                )
            } else {
                backToHome()
            }
        }
    }
}