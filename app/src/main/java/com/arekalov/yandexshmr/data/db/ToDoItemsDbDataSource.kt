package com.arekalov.yandexshmr.data.db

import com.arekalov.yandexshmr.data.common.ADD_ERROR
import com.arekalov.yandexshmr.data.common.DELETE_ERROR
import com.arekalov.yandexshmr.data.common.GET_ERROR
import com.arekalov.yandexshmr.data.common.UPDATE_ERROR
import com.arekalov.yandexshmr.data.common.mapToDoItemModelToListItemModel
import com.arekalov.yandexshmr.data.db.dao.RevisionDao
import com.arekalov.yandexshmr.data.db.dao.ToDoItemsDao
import com.arekalov.yandexshmr.data.db.dto.RevisionDbDto
import com.arekalov.yandexshmr.data.db.mappers.toToDoItemElementDbDto
import com.arekalov.yandexshmr.data.db.mappers.toToDoItemModel
import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.util.Resource
import javax.inject.Inject

class ToDoItemsDbDataSource @Inject constructor(
    private val toDoItemsDao: ToDoItemsDao,
    private val revisionDao: RevisionDao
) {

    suspend fun updateAll(itemListModel: ToDoItemListModel) {
        toDoItemsDao.deleteAllToDoItems()
        for (i in itemListModel.items) {
            toDoItemsDao.addToDoItem(item = i.toToDoItemElementDbDto())
        }
    }
    suspend fun getRevision(): Int {
        try {
            return revisionDao.getRevision().revisionNumber
        } catch (ex: Exception) {
            revisionDao.insertOrUpdateRevision(RevisionDbDto())
            return revisionDao.getRevision().revisionNumber
        }
    }

    suspend fun setRevision(revision: Int) {
        revisionDao.updateRevisionTo(revision)
    }

    private suspend fun incrementRevision() {
        revisionDao.incrementRevision()
    }

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
            incrementRevision()
            Resource.Success(null)
        } catch (ex: Exception) {
            Resource.Error(DELETE_ERROR)

        }
    }

    suspend fun updateItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val elementToUpdate = item.toToDoItemElementDbDto()
            toDoItemsDao.updateToDoItem(elementToUpdate)
            incrementRevision()
            Resource.Success(item)
        } catch (ex: Exception) {
            Resource.Error(UPDATE_ERROR)

        }
    }

    suspend fun addItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToAdd = item.toToDoItemElementDbDto()
            toDoItemsDao.addToDoItem(itemToAdd)
            incrementRevision()
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
            incrementRevision()
            Resource.Success(emptyToDoItemModel)
        }
    }

    suspend fun updateOrAddItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        return try {
            val itemToSave = item.toToDoItemElementDbDto()
            toDoItemsDao.getToDoItem(item.id).toToDoItemModel()
            toDoItemsDao.updateToDoItem(itemToSave)
            incrementRevision()
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