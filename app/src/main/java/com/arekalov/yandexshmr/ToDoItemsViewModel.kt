package com.arekalov.yandexshmr

import androidx.lifecycle.ViewModel
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.models.ToDoItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ToDoItemsViewModel : ViewModel() {
    private val repository = ToDoItemRepository()
    private val list = repository.itemList
    private val _items = MutableStateFlow(list)
    val items: StateFlow<List<ToDoItem>> = _items.asStateFlow()

    fun changeIsDone(item: ToDoItem) {
        _items.update {
            val nowList = it.toMutableList()
            val ind = nowList.indexOf(item)
            nowList[ind] = item.copy(isDone = !item.isDone)
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

    fun deleteItem(id: String?) {
        _items.update {
            val nowList = it.toMutableList()
            val el = nowList.find { it.id == id }
            nowList.remove(el)
            nowList
        }
    }

    fun update(id: String, item: ToDoItem) {
        _items.update {
            val nowList = it.toMutableList()
            val ind = nowList.indexOf(nowList.find { it.id == id })
            nowList[ind] = item
            nowList
        }
    }

    fun getItem(id: String): ToDoItem? {
        return _items.value.find { it.id == id }
    }

    fun isItemExists(id: String): Boolean {
        return _items.value.find { it.id == id } != null
    }
}
