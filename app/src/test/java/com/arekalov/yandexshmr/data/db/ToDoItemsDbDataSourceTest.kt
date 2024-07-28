package com.arekalov.yandexshmr.data.db

import com.arekalov.yandexshmr.data.common.ADD_ERROR
import com.arekalov.yandexshmr.data.common.DELETE_ERROR
import com.arekalov.yandexshmr.data.common.GET_ERROR
import com.arekalov.yandexshmr.data.common.mapToDoItemModelToListItemModel
import com.arekalov.yandexshmr.data.db.dao.RevisionDao
import com.arekalov.yandexshmr.data.db.dao.ToDoItemsDao
import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto
import com.arekalov.yandexshmr.data.db.mappers.toToDoItemElementDbDto
import com.arekalov.yandexshmr.data.db.mappers.toToDoItemModel
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ToDoItemsDbDataSourceTest {
    private val toDoItemsDao = mockk<ToDoItemsDao>(relaxed = true)
    private val revisionDao = mockk<RevisionDao>(relaxed = true)
    private lateinit var sut: ToDoItemsDbDataSource

    @BeforeEach
    fun setUp() {
        sut = ToDoItemsDbDataSource(toDoItemsDao = toDoItemsDao, revisionDao = revisionDao)
    }

    @Test
    fun `given ToDoItemModel should return Resource(ToDoItemModel) of existed itemModel and update it when this item exists in db`() =
        runTest {
            val dto = ToDoItemElementDbDto(
                id = "1",
                itemType = "",
                task = "task 1",
                voicePath = null,
                priority = Priority.LOW,
                deadline = null,
                isDone = true,
                creationDate = LocalDate.now().toString(),
                editDate = null
            )
            val model = ToDoItemModel(
                id = "1",
                task = "task 1",
                priority = Priority.LOW,
                isDone = true,
                creationDate = LocalDate.now()
            )
            val expected = Resource.Success(
                model
            )
            val actual = sut.updateOrAddItem(model)
            coVerify { toDoItemsDao.getToDoItem("1") }
//            coVerify { toDoItemsDao.updateToDoItem(dto) }
            coVerify { revisionDao.incrementRevision() }
            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given ToDoItemModel should return Resource-Success(ToDoItemModel) of itemModel and save it in db when this item not exists in db`() =
        runTest {
            val dto = ToDoItemElementDbDto(
                id = "???",
                itemType = "",
                task = "task 1",
                voicePath = null,
                priority = Priority.LOW,
                deadline = null,
                isDone = true,
                creationDate = LocalDate.now().toString(),
                editDate = null
            )
            val model = ToDoItemModel(
                id = "???",
                task = "task 1",
                priority = Priority.LOW,
                isDone = true,
                creationDate = LocalDate.now()
            )
            val expected = Resource.Success(
                model
            )
            coEvery { toDoItemsDao.getToDoItem("???") } throws Exception()

            val actual = sut.updateOrAddItem(model)
            coVerify(exactly = 1) { toDoItemsDao.addToDoItem(dto) }
            assertThat(actual).isEqualTo(expected)
        }


    @Test
    fun `given ToDoItemModel should return Resource-Success(ToDoItemModel) of itemModel and save it in db when dao can't update existed item`() =
        runTest {
            val dto = ToDoItemElementDbDto(
                id = "???",
                itemType = "",
                task = "task 1",
                voicePath = null,
                priority = Priority.LOW,
                deadline = null,
                isDone = true,
                creationDate = LocalDate.now().toString(),
                editDate = null
            )
            val model = ToDoItemModel(
                id = "???",
                task = "task 1",
                priority = Priority.LOW,
                isDone = true,
                creationDate = LocalDate.now()
            )
            val expected = Resource.Success(
                model
            )
            coEvery { toDoItemsDao.updateToDoItem(dto) } throws Exception()

            val actual = sut.updateOrAddItem(model)
            coVerify(exactly = 1) { toDoItemsDao.addToDoItem(dto) }
            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given ToDoItemModel should return Resource-Error(ToDoItemModel) of ADD_ERROR when toDoItemsDao-addItem() throw exception`() =
        runTest {
            val dto = ToDoItemElementDbDto(
                id = "???",
                itemType = "",
                task = "task 1",
                voicePath = null,
                priority = Priority.LOW,
                deadline = null,
                isDone = true,
                creationDate = LocalDate.now().toString(),
                editDate = null
            )
            val model = ToDoItemModel(
                id = "???",
                task = "task 1",
                priority = Priority.LOW,
                isDone = true,
                creationDate = LocalDate.now()
            )
            val expected = Resource.Error<ToDoItemModel>(
                ADD_ERROR
            )
            coEvery { toDoItemsDao.updateToDoItem(dto) } throws Exception()
            coEvery { toDoItemsDao.addToDoItem(dto) } throws Exception()

            val actual = sut.updateOrAddItem(model)
            coVerify(exactly = 1) { toDoItemsDao.addToDoItem(dto) }
            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given toDoItemModel should save it in db and return Resource-Success(toDoItemModel)`() =
        runTest {
            val model = ToDoItemModel(
                id = "1",
                task = "task",
                priority = Priority.REGULAR,
                isDone = true,
                creationDate = LocalDate.now()
            )
            val dto = model.toToDoItemElementDbDto()

            val expected = Resource.Success(model)
            val actual = sut.addItem(model)

            assertThat(actual).isEqualTo(expected)
            coVerifyOrder {
                toDoItemsDao.addToDoItem(dto)
                revisionDao.incrementRevision()
            }
        }

    @Test
    fun `given toDoItemModel should return Resource-Error with error message when toDoItemsDao throw Exception`() =
        runTest {
            val model = ToDoItemModel(
                id = "1",
                task = "task",
                priority = Priority.REGULAR,
                isDone = true,
                creationDate = LocalDate.now()
            )
            val dto = model.toToDoItemElementDbDto()
            coEvery { toDoItemsDao.addToDoItem(dto) } throws Exception()

            val expected = Resource.Error<ToDoItemModel>(ADD_ERROR)
            val actual = sut.addItem(model)
            coVerify(exactly = 0) { revisionDao.incrementRevision() }

            assertThat(actual).isEqualTo(expected)

        }

    @Test
    fun `given toDoItemModel should return Resource-Error with error message when revisionDao throw Exception`() =
        runTest {
            val model = ToDoItemModel(
                id = "1",
                task = "task",
                priority = Priority.REGULAR,
                isDone = true,
                creationDate = LocalDate.now()
            )
            coEvery { revisionDao.incrementRevision() } throws Exception()

            val expected = Resource.Error<ToDoItemModel>(ADD_ERROR)
            val actual = sut.addItem(model)

            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `should return Resource-Success(ToDoItemsListModel) when toDoItemsDao work correct`() =
        runTest {
            val dtoList = listOf(
                ToDoItemElementDbDto(
                    id = "1",
                    itemType = "",
                    task = "task 1",
                    voicePath = null,
                    priority = Priority.LOW,
                    deadline = null,
                    isDone = true,
                    creationDate = LocalDate.now().toString(),
                    editDate = null
                ),
                ToDoItemElementDbDto(
                    id = "2",
                    itemType = "",
                    task = "task 1",
                    voicePath = null,
                    priority = Priority.LOW,
                    deadline = null,
                    isDone = true,
                    creationDate = LocalDate.now().toString(),
                    editDate = null
                )
            )
            val toDoItemListWithMapModel =
                mapToDoItemModelToListItemModel(dtoList.map { it.toToDoItemModel() })
            coEvery { toDoItemsDao.getToDoItems() } returns dtoList
            val expected = Resource.Success(toDoItemListWithMapModel)

            val actual = sut.getToDoItemListModel()

            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `should return Resource-Error(GET_ERROR) when toDoItemsDao-getItems() throw exception`() =
        runTest {
            coEvery { toDoItemsDao.getToDoItems() } throws Exception()
            val expected = Resource.Error<ToDoItemListModel>(GET_ERROR)

            val actual = sut.getToDoItemListModel()

            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given id and empty ToDoItem should return toDoItem with this id when it exists`() =
        runTest {
            val id = "1"
            val dto = ToDoItemElementDbDto(
                id = "1",
                itemType = "",
                task = "task 1",
                voicePath = null,
                priority = Priority.LOW,
                deadline = null,
                isDone = true,
                creationDate = LocalDate.now().toString(),
                editDate = null
            )
            val empty = ToDoItemModel(
                id = "1",
                task = "task",
                priority = Priority.REGULAR,
                isDone = true,
                creationDate = LocalDate.now()
            )
            coEvery { toDoItemsDao.getToDoItem(id) } returns dto
            val expected = Resource.Success(dto.toToDoItemModel())

            val actual = sut.getOrCreate(id, empty)

            assertThat(actual).isEqualTo(expected)
            coVerify(exactly = 0) { revisionDao.incrementRevision() }
        }

    @Test
    fun `given id and empty ToDoItem should return empty toDoItem when it not exists`() = runTest {
        val id = "2"
        val empty = ToDoItemModel(
            id = "1",
            task = "task",
            priority = Priority.REGULAR,
            isDone = true,
            creationDate = LocalDate.now()
        )
        coEvery { toDoItemsDao.getToDoItem(id) } throws Exception()
        val expected = Resource.Success(empty)

        val actual = sut.getOrCreate(id, empty)

        assertThat(actual).isEqualTo(expected)
        coVerify(exactly = 1) { revisionDao.incrementRevision() }
    }

    @Test
    fun `given id should return Resource-Success(null) and remove item from db when item exists`() =
        runTest {
            val dto = ToDoItemElementDbDto(
                id = "1",
                itemType = "",
                task = "task 1",
                voicePath = null,
                priority = Priority.LOW,
                deadline = null,
                isDone = true,
                creationDate = LocalDate.now().toString(),
                editDate = null
            )
            val expected = Resource.Success(null)

            val actual = sut.deleteItem(dto.id)

            assertThat(actual).isEqualTo(expected)
            coVerify(exactly = 1) { toDoItemsDao.deleteToDoItem(dto.id) }
            coVerify(exactly = 1) { revisionDao.incrementRevision() }
        }

    @Test
    fun `given id should return Resource-Error(errorMessage) when api throw exception`() = runTest {
        val expected = Resource.Error<ToDoItemModel>(DELETE_ERROR)
        coEvery { toDoItemsDao.deleteToDoItem(any()) } throws Exception()

        val actual = sut.deleteItem("??")

        assertThat(actual).isEqualTo(expected)
        coVerify(exactly = 0) { revisionDao.incrementRevision() }
    }
}
