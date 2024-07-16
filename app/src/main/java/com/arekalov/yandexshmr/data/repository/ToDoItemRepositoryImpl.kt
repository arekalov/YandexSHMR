package com.arekalov.yandexshmr.data.repository

import com.arekalov.yandexshmr.data.common.GET_ERROR
import com.arekalov.yandexshmr.data.db.ToDoItemsDbDataSource
import com.arekalov.yandexshmr.data.network.ToDoItemsNetworkDataSource
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import com.arekalov.yandexshmr.domain.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
Repository that help change data with server. Contains logic and help catch errors
 **/

@Singleton
class ToDoItemRepositoryImpl @Inject constructor(
    private val networkDataSource: ToDoItemsNetworkDataSource,
    private val dbDataSource: ToDoItemsDbDataSource
) : ToDoItemRepository {
    private val _todoItems: MutableStateFlow<Resource<ToDoItemListModel>> =
        MutableStateFlow(Resource.Success(data = null))
    override val todoItems: StateFlow<Resource<ToDoItemListModel>>
        get() = _todoItems

    private val coroutineContext = Dispatchers.IO

    override suspend fun getToDoItemListModel(): Resource<ToDoItemListModel> {
        return dbDataSource.getToDoItemListModel()
    }

    override suspend fun getOrCreateItem(id: String): Resource<ToDoItemModel> {
        val response = dbDataSource.getOrCreate(id, getEmptyToDoItemModel())
        updateFlowFromDb()
        CoroutineScope(coroutineContext).launch {
            networkDataSource.getOrCreateItem(id = id, emptyItem = getEmptyToDoItemModel())
        }
        return response
    }

    override suspend fun getItem(id: String): Resource<ToDoItemModel> {
        return dbDataSource.getItem(id)
    }

    override suspend fun deleteItem(id: String): Resource<ToDoItemModel> {
        val res = dbDataSource.deleteItem(id)
        updateFlowFromDb()
        CoroutineScope(coroutineContext).launch {
            networkDataSource.deleteItem(id = id)
        }
        return res
    }

    override suspend fun updateItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel> {
        val res = dbDataSource.updateItem(item = item)
        updateFlowFromDb()
        CoroutineScope(coroutineContext).launch {
            networkDataSource.updateItem(id = id, item = item)
        }
        return res
    }

    override suspend fun addItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        val res = dbDataSource.addItem(item)
        updateFlowFromDb()
        CoroutineScope(coroutineContext).launch {
            networkDataSource.addItem(item = item)
        }
        return res
    }

    override suspend fun updateToDoItemsFlow() {
        if (networkDataSource.getRevision() >= dbDataSource.getRevision()) {
            val newData = networkDataSource.getToDoItemListModel()
            if (newData is Resource.Success) {
                dbDataSource.updateAll(newData.data!!)
                updateFlowFromDb()
            } else {
                _todoItems.value = Resource.Error(GET_ERROR)
            }
        } else {
            updateFlowFromDb()
            dbDataSource.getToDoItemListModel().data?.let { networkDataSource.updateAllItems(it) }
        }
        dbDataSource.setRevision(networkDataSource.getRevision())

    }

    override suspend fun updateOrAddItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel> {
        val res = dbDataSource.updateOrAddItem(item)
        updateFlowFromDb()
        CoroutineScope(coroutineContext).launch {
            networkDataSource.updateOrAddItem(id = item.id, item = item)
        }
        return res
    }

    private suspend fun updateFlowFromDb() {
        _todoItems.value = dbDataSource.getToDoItemListModel()
    }

    override fun getEmptyToDoItemModel(): ToDoItemModel {
        return ToDoItemModel(
            id = UUID.randomUUID().toString(),
            task = "",
            priority = Priority.REGULAR,
            isDone = false,
            creationDate = LocalDate.now()
        )
    }

}
