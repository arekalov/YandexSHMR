package com.arekalov.yandexshmr

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.models.ToDoItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ToDoItemsViewModel : ViewModel() {
    // Временное решение, которое заменится при реальном репизотории
    private val repository = ToDoItemRepository()
    private val list = repository.itemListFlow.value
    private val _items = MutableStateFlow(list)
    val items: StateFlow<List<ToDoItem>> = _items.asStateFlow()

    fun changeIsDone(item: ToDoItem) {
        _items.update {
            val nowList = it.toMutableList()
            val ind = nowList.indexOf(item)
            nowList[ind] = item.copy(isDone = mutableStateOf(!item.isDone.value))
            nowList
        }
    }

    fun addItem(item: ToDoItem) {
        _items.update {
            val nowList = it.toMutableList()
            nowList.add(item)
            nowList
        }
    }

    fun deleteItem(item: ToDoItem) {
        _items.update {
            val nowList = it.toMutableList()
            nowList.remove(item)
            nowList
        }
    }

    fun update(id: String, item: ToDoItem) {
        _items.update {
            val nowList = it.toMutableList()
            val ind = nowList.indexOf(nowList.find { it.id.value == id })
            nowList[ind] = item
            nowList
        }
    }
}
