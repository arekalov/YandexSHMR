package com.arekalov.yandexshmr.data.repository

import android.util.Log
import com.arekalov.yandexshmr.data.common.mapToDoItemModelToListItemModel
import com.arekalov.yandexshmr.data.db.ToDoItemsDao
import com.arekalov.yandexshmr.data.db.ToDoItemsDbDataSource
import com.arekalov.yandexshmr.data.network.mappers.toToDoItemElementToSend
import com.arekalov.yandexshmr.data.network.mappers.toToDoItemListModel
import com.arekalov.yandexshmr.data.network.mappers.toToDoItemModel
import com.arekalov.yandexshmr.data.network.ToDoItemApi
import com.arekalov.yandexshmr.data.network.ToDoItemsNetworkDataSource
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

/**
Repository that help change data with server. Contains logic and help catch errors
 **/


class ToDoItemRepositoryImpl(
    private val networkDataSource: ToDoItemsNetworkDataSource,
    private val dbDataSource: ToDoItemsDbDataSource
) : ToDoItemRepository {
    private var revision: Int = -1

    private val _todoItems: MutableStateFlow<Resource<ToDoItemListModel>> =
        MutableStateFlow(Resource.Success(data = null))
    override val todoItems: StateFlow<Resource<ToDoItemListModel>>
        get() = _todoItems

    override suspend fun getToDoItemListModel(): Resource<ToDoItemListModel> {
        return dbDataSource.getToDoItemListModel()
    }

    override suspend fun getOrCreateItem(id: String): Resource<ToDoItemModel> {
        val response = dbDataSource.getOrCreate(id, getEmptyToDoItemModel())
        updateFlowFromDb()
        return response
    }

    override suspend fun getItem(id: String): Resource<ToDoItemModel> {
        return dbDataSource.getItem(id)
    }

    override suspend fun deleteItem(id: String): Resource<ToDoItemModel> {
        val res = dbDataSource.deleteItem(id)
        updateFlowFromDb()
        return res
    }

    override suspend fun updateItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel> {
        val res = dbDataSource.updateItem(item = item)
        updateFlowFromDb()
        return res
    }

    override suspend fun addItem(item: ToDoItemModel): Resource<ToDoItemModel> {
        val res = dbDataSource.addItem(item)
        updateFlowFromDb()
        return res
    }

    override suspend fun updateToDoItemsFlow() {
        updateFlowFromDb()
    }

    override suspend fun updateOrAddItem(id: String, item: ToDoItemModel): Resource<ToDoItemModel> {
        val res = dbDataSource.updateOrAddItem(item)
        updateFlowFromDb()
        return res
    }

    suspend fun updateFlowFromNetwork() {
        _todoItems.value = networkDataSource.getToDoItemListModel()
    }

    suspend fun updateFlowFromDb() {
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
