package com.arekalov.yandexshmr.data.network

import android.util.Log
import com.arekalov.yandexshmr.data.common.mapToDoItemModelToListItemModel
import com.arekalov.yandexshmr.data.network.mappers.toToDoItemElementToSend
import com.arekalov.yandexshmr.data.network.mappers.toToDoItemListModel
import com.arekalov.yandexshmr.data.network.mappers.toToDoItemModel
import com.arekalov.yandexshmr.data.common.ADD_ERROR
import com.arekalov.yandexshmr.data.common.DELETE_ERROR
import com.arekalov.yandexshmr.data.common.GET_ERROR
import com.arekalov.yandexshmr.data.common.MERGE_ERROR
import com.arekalov.yandexshmr.data.common.UPDATE_ERROR
import com.arekalov.yandexshmr.data.network.mappers.toToDoItemListToSendNetworkDto
import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.util.Resource
import kotlinx.coroutines.delay
import kotlin.math.max

class ToDoItemsNetworkDataSource(
    private val toDoItemApi: ToDoItemApi
) {
    private var revision: Int = -1
    suspend fun getRevision(): Int {
        getToDoItemListModel()
        return revision

    }

    suspend fun getOrCreateItem(id: String, emptyItem: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val response = toDoItemApi.getToDoItem(id)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Success(emptyItem)
            }
        } catch (ex: Exception) {
            Resource.Error(GET_ERROR)
        }
    }

    suspend fun getToDoItemListModel(): Resource<ToDoItemListModel> {
        return try {
            val response = toDoItemApi.getToDoItems()
            if (response.isSuccessful) {
                val toDoItemListDto = response.body()!!
                revision = max(revision, toDoItemListDto.revision)
                val toDoItemListWithMapModel =
                    mapToDoItemModelToListItemModel(toDoItemListDto.toToDoItemListModel())
                Resource.Success(toDoItemListWithMapModel)
            } else {
                Resource.Error(GET_ERROR)
            }
        } catch (ex: Exception) {
            Resource.Error(GET_ERROR)
        }
    }

    suspend fun getItem(id: String): Resource<ToDoItemModel> {
        return try {
            val response = toDoItemApi.getToDoItem(id)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Error(response.message())
            }
        } catch (ex: Exception) {
            Resource.Error(GET_ERROR)
        }
    }

    suspend fun deleteItem(id: String): Resource<ToDoItemModel> {
        return try {
            val revision = getRevision()
            val response = toDoItemApi.deleteToDoItem(revision, id)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Error(DELETE_ERROR)
            }
        } catch (ex: Exception) {
            Resource.Error(DELETE_ERROR)
        }
    }

    suspend fun updateItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToSend = item.toToDoItemElementToSend()
            val response = toDoItemApi.updateToDoItem(getRevision(), id, itemToSend)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Error(UPDATE_ERROR)
            }
        } catch (ex: Exception) {
            Resource.Error(UPDATE_ERROR)
        }
    }

    suspend fun updateAllItems(items: ToDoItemListModel): Resource<ToDoItemListModel> {
        return try {
            val itemsToSend = items.toToDoItemListToSendNetworkDto()
            val response = toDoItemApi.updateToDoList(getRevision(), itemsToSend)
            if (response.isSuccessful) {
                Resource.Success(items)
            } else {
                Resource.Error(MERGE_ERROR)
            }
        } catch (ex: Exception) {
            Resource.Error(MERGE_ERROR)
        }
    }

    suspend fun addItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToSend = item.toToDoItemElementToSend()
            val response = toDoItemApi.addToDoItem(getRevision(), itemToSend)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Error(ADD_ERROR)
            }
        } catch (ex: Exception) {
            Resource.Error(ADD_ERROR)
        }
    }

    suspend fun updateOrAddItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToSend = item.toToDoItemElementToSend()
            val response = toDoItemApi.updateToDoItem(getRevision(), id, itemToSend)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                Resource.Success(toDoItemDto)
            } else {
                addItem(item)
            }
        } catch (ex: Exception) {
            Resource.Error(UPDATE_ERROR)
        }
    }
}