package com.arekalov.yandexshmr.data.repository

import com.arekalov.yandexshmr.data.common.mapToDoItemModelToListItemModel
import com.arekalov.yandexshmr.data.db.ToDoItemsDbDataSource
import com.arekalov.yandexshmr.data.db.mappers.toToDoItemElementDbDto
import com.arekalov.yandexshmr.data.db.mappers.toToDoItemModel
import com.arekalov.yandexshmr.data.network.ToDoItemsNetworkDataSource
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ToDoItemRepositoryImplTest {
    private val networkDataSource = mockk<ToDoItemsNetworkDataSource>()
    private val dbdDataSource = mockk<ToDoItemsDbDataSource>()
    private lateinit var sut: ToDoItemRepositoryImpl

    @BeforeEach
    fun setUp() {
        sut = ToDoItemRepositoryImpl(networkDataSource, dbdDataSource)
    }

    @Test
    fun `given toDoItemModel should return this toDoItemModel and update state when toDoItemModel exists`() =
        runTest {
            val item = ToDoItemModel(
                id = "1",
                task = "task item",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val dtoList = listOf(
                item.toToDoItemElementDbDto()
            )
            val toDoItemListWithMapModel =
                mapToDoItemModelToListItemModel(dtoList.map { it.toToDoItemModel() })

            coEvery { dbdDataSource.updateOrAddItem(item) } returns Resource.Success(item)
            coEvery { networkDataSource.updateOrAddItem(item.id, item) } returns Resource.Success(
                item
            )
            coEvery { dbdDataSource.getToDoItemListModel() } returns Resource.Success(
                toDoItemListWithMapModel
            )
            val expected = Resource.Success(item)

            val actual = sut.updateOrAddItem("1", item)
            assertThat(actual).isEqualTo(expected)
            assertThat(sut.todoItems.value).isEqualTo(Resource.Success(toDoItemListWithMapModel))
            coVerify { networkDataSource.updateOrAddItem("1", item) }
        }


    @Test
    fun `given toDoItemModel should add this model to network and db return this toDoItemModel and update state when toDoItemModel not exists`() =
        runTest {
            val item = ToDoItemModel(
                id = "1",
                task = "task item",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val itemExisted = ToDoItemModel(
                id = "2",
                task = "task item",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val dtoListAfterAdd = listOf(
                itemExisted.toToDoItemElementDbDto(),
                item.toToDoItemElementDbDto()
            )
            val toDoItemListExpected =
                mapToDoItemModelToListItemModel(dtoListAfterAdd.map { it.toToDoItemModel() })
            coEvery { dbdDataSource.updateOrAddItem(item) } returns Resource.Success(item)
            coEvery { networkDataSource.updateOrAddItem(item.id, item) } returns Resource.Success(
                item
            )
            coEvery { dbdDataSource.getToDoItemListModel() } returns Resource.Success(
                toDoItemListExpected
            )
            val expected = Resource.Success(item)

            val actual = sut.updateOrAddItem("1", item)

            assertThat(actual).isEqualTo(expected)
            println(sut.todoItems.value.data)
            println(toDoItemListExpected)
            assertThat(sut.todoItems.value).isEqualTo(Resource.Success(toDoItemListExpected))
            coVerify { networkDataSource.updateOrAddItem("1", item) }
        }

    @Test
    fun `given id should return Resource-Success(toDoItem), save it on db and net when item exists`() =
        runTest {
            val empty = ToDoItemModel(
                id = "1",
                task = "task item",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val item = ToDoItemModel(
                id = "2",
                task = "task item",
                priority = Priority.HIGH,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val toDoItemListWithMapModel =
                mapToDoItemModelToListItemModel(listOf(item))
            coEvery { dbdDataSource.getOrCreate(item.id, any()) } returns Resource.Success(item)
            coEvery { networkDataSource.getOrCreateItem(item.id, any()) } returns Resource.Success(
                item
            )
            coEvery { dbdDataSource.getToDoItemListModel() } returns Resource.Success(
                toDoItemListWithMapModel
            )
            val expected = Resource.Success(item)

            val actual = sut.getOrCreateItem(item.id)

            assertThat(actual).isEqualTo(expected)
            assertThat(sut.todoItems.value.data).isEqualTo(
                mapToDoItemModelToListItemModel(
                    listOf(item)
                )
            )
        }

}
