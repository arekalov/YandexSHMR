package com.arekalov.yandexshmr.data.repository

import com.arekalov.yandexshmr.data.api.ToDoItemApi
import com.arekalov.yandexshmr.data.mappers.mapToDoItemModelToListItemModel
import com.arekalov.yandexshmr.data.mappers.toToDoItemElementToSend
import com.arekalov.yandexshmr.data.mappers.toToDoItemListModel
import com.arekalov.yandexshmr.data.mappers.toToDoItemModel
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import com.arekalov.yandexshmr.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.util.UUID
import kotlin.math.max

class ToDoItemRepositoryImpl(
    private val toDoItemApi: ToDoItemApi
) : ToDoItemRepository { // временно public, после внедрения di станет internal
    private var revision: Int = -1

    private val _todoItems: MutableStateFlow<Resource<ToDoItemListModel>> =
        MutableStateFlow(Resource.Success(data = null))
    override val todoItems: StateFlow<Resource<ToDoItemListModel>>
        get() = _todoItems

    override suspend fun updateToDoItemsFlow() {
        _todoItems.value = getToDoItemListModel()
    }


    private suspend fun getRevision(): Int {
        return if (revision == -1) {
            getToDoItemListModel()
            revision
        } else {
            revision
        }
    }

    override suspend fun getToDoItemListModel(): Resource<ToDoItemListModel> {
        return try {
            val response = toDoItemApi.getToDoList()
            if (response.isSuccessful) {
                val toDoItemListDto = response.body()!!
                revision = max(revision, toDoItemListDto.revision)
                val toDoItemListWithMapModel =
                    mapToDoItemModelToListItemModel(toDoItemListDto.toToDoItemListModel())
                Resource.Success(toDoItemListWithMapModel)
            } else {
                Resource.Error(response.message())
            }
        } catch (ex: Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    override suspend fun getOrCreateItem(id: String): Resource<ToDoItemModel> {
        return try {
            val response = toDoItemApi.getToDoItem(id)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                updateToDoItemsFlow()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Success(getEmptyToDoItemModel())
            }
        } catch (ex: Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    override suspend fun getItem(id: String): Resource<ToDoItemModel> {
        return try {
            val response = toDoItemApi.getToDoItem(id)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                updateToDoItemsFlow()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Error(response.message())
            }
        } catch (ex: Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    override suspend fun deleteItem(id: String): Resource<ToDoItemModel> {
        return try {
            val response = toDoItemApi.deleteToDoItem(getRevision(), id)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                updateToDoItemsFlow()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Error(response.message())
            }
        } catch (ex: Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    override suspend fun updateItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToSend = item.toToDoItemElementToSend()
            val response = toDoItemApi.updateToDoItem(getRevision(), id, itemToSend)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                updateToDoItemsFlow()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Error(response.message())
            }
        } catch (ex: Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    override suspend fun addItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToSend = item.toToDoItemElementToSend()
            val response = toDoItemApi.addToDoItem(getRevision(), itemToSend)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                updateToDoItemsFlow()
                Resource.Success(toDoItemDto)
            } else {
                Resource.Error(response.message())
            }
        } catch (ex: Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    override suspend fun updateOrAddItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToSend = item.toToDoItemElementToSend()
            val response = toDoItemApi.updateToDoItem(getRevision(), id, itemToSend)
            if (response.isSuccessful) {
                val toDoItemDto = response.body()!!.toToDoItemModel()
                updateToDoItemsFlow()
                Resource.Success(toDoItemDto)
            } else {
                addItem(item)
            }
        } catch (ex: Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    private fun getEmptyToDoItemModel(): ToDoItemModel {
        return ToDoItemModel(
            id = UUID.randomUUID().toString(),
            task = "",
            priority = Priority.REGULAR,
            isDone = false,
            creationDate = LocalDate.now()
        )
    }

}