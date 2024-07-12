package com.arekalov.yandexshmr.data.db

import android.content.ContentValues.TAG
import android.util.Log
import com.arekalov.yandexshmr.data.common.ADD_ERROR
import com.arekalov.yandexshmr.data.common.DELETE_ERROR
import com.arekalov.yandexshmr.data.common.GET_ERROR
import com.arekalov.yandexshmr.data.common.UPDATE_ERROR
import com.arekalov.yandexshmr.data.common.mapToDoItemModelToListItemModel
import com.arekalov.yandexshmr.data.db.mappers.toToDoItemElementDbDto
import com.arekalov.yandexshmr.data.db.mappers.toToDoItemModel
import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.util.Resource

class ToDoItemsDbDataSource(
    private val toDoItemsDao: ToDoItemsDao
) {
    suspend fun getToDoItemListModel(): Resource<ToDoItemListModel> {
        return try {
            val response = toDoItemsDao.getToDoItems()
            val toDoItemListWithMapModel =
                mapToDoItemModelToListItemModel(response.map { it.toToDoItemModel() })
            Resource.Success(toDoItemListWithMapModel)
        } catch (ex: Exception) {
            Resource.Error(GET_ERROR)
        }
    }

    suspend fun getItem(id: String): Resource<ToDoItemModel> {
        return try {
            val response = toDoItemsDao.getToDoItem(id)
            val toDoItemDto = response.toToDoItemModel()
            Resource.Success(toDoItemDto)
        } catch (ex: Exception) {
            Resource.Error(GET_ERROR)
        }
    }

    suspend fun deleteItem(id: String): Resource<ToDoItemModel> {
        return try {
            toDoItemsDao.deleteToDoItem(id)
            Resource.Success(null)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString(), )
            Resource.Error(DELETE_ERROR)

        }
    }

    suspend fun updateItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val elementToUpdate = item.toToDoItemElementDbDto()
            toDoItemsDao.updateToDoItem(elementToUpdate)
            Resource.Success(item)
        } catch (ex: Exception) {
            Resource.Error(UPDATE_ERROR)

        }
    }

    suspend fun addItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToAdd = item.toToDoItemElementDbDto()
            toDoItemsDao.addToDoItem(itemToAdd)
            Resource.Success(item)
        } catch (ex: Exception) {
            Resource.Error(ADD_ERROR)
        }
    }

    suspend fun getOrCreate(
        id: String,
        emptyToDoItemModel: ToDoItemModel
    ): Resource<ToDoItemModel> {
        return try {
            Resource.Success(toDoItemsDao.getToDoItem(id).toToDoItemModel())
        } catch (ex: Exception) {
            Resource.Success(emptyToDoItemModel)
        }
    }

    suspend fun updateOrAddItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToSave = item.toToDoItemElementDbDto()
            toDoItemsDao.getToDoItem(item.id).toToDoItemModel()
            toDoItemsDao.updateToDoItem(itemToSave)
            Resource.Success(item)
        } catch (ex: Exception) {
            try {
                addItem(item)
            } catch (ex: Exception) {
                Resource.Error(UPDATE_ERROR)
            }
        }
    }
}