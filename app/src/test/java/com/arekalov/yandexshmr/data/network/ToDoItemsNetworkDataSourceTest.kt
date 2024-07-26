package com.arekalov.yandexshmr.data.network

import com.arekalov.yandexshmr.data.common.GET_ERROR
import com.arekalov.yandexshmr.data.network.dto.ToDoItemElementNetworkDto
import com.arekalov.yandexshmr.data.network.dto.ToDoItemNetworkDto
import com.arekalov.yandexshmr.data.network.mappers.toEpochMilli
import com.arekalov.yandexshmr.data.network.mappers.toToDoItemModel
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.time.LocalDate
import java.util.UUID

class ToDoItemsNetworkDataSourceTest {
    private val todoItemApi = mockk<ToDoItemApi>()
    private lateinit var sut: ToDoItemsNetworkDataSource

    @BeforeEach
    fun setUp() {
        sut = ToDoItemsNetworkDataSource(todoItemApi)
    }

    @Test
    fun `given id should return Resource-Success(ToDoItemModel) of this item when item exists`() =
        runTest {
            val id = "id1"
            val empty = ToDoItemModel(
                id = UUID.randomUUID().toString(),
                task = "",
                priority = Priority.REGULAR,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val dto = ToDoItemElementNetworkDto(
                status = "ok",
                revision = 1244,
                element = ToDoItemNetworkDto(
                    done = true,
                    created_at = LocalDate.now().toEpochMilli(),
                    text = "My task",
                    changed_at = LocalDate.now().toEpochMilli(),
                    id = id,
                    importance = "basic",
                    last_updated_by = "null",
                    color = null,
                    deadline = null
                )
            )
            val model = ToDoItemModel(
                id = id,
                task = "My task",
                priority = Priority.REGULAR,
                deadline = null,
                isDone = true,
                creationDate = LocalDate.now(),
                editDate = null
            )
            val apiResp = Response.success(dto)
            val expected = Resource.Success(model)
            coEvery { todoItemApi.getToDoItem(id) } returns apiResp

            val actual = sut.getOrCreateItem(
                id, empty
            )

            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given error id should return Resource-Success(ToDoItemModel) of empty item when item not exists`() =
        runTest {
            val empty = ToDoItemModel(
                id = UUID.randomUUID().toString(),
                task = "",
                priority = Priority.REGULAR,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val apiResp =
                Response.error<ToDoItemElementNetworkDto>(404, "Not Found".toResponseBody())
            val expected = Resource.Success(empty)
            coEvery { todoItemApi.getToDoItem("not real id") } returns apiResp

            val actual = sut.getOrCreateItem(
                "not real id", empty
            )
            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given id should throw exception in api and return Resource-Error(ToDoItemModel) with text error message`() =
        runTest {
            val empty = ToDoItemModel(
                id = UUID.randomUUID().toString(),
                task = "",
                priority = Priority.REGULAR,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val expected = Resource.Error<ToDoItemModel>(message = GET_ERROR)
            coEvery { todoItemApi.getToDoItem("not real id") } throws Exception()

            val actual = sut.getOrCreateItem(
                "not real id", empty
            )
            assertThat(actual).isEqualTo(expected)
        }

    @Test
    fun `given id and empty toDoItemModel should return Resource-Success(toDoItemModel) of this model when item exists`() =
        runTest {
            val dto = ToDoItemElementNetworkDto(
                status = "ok",
                revision = 1,
                element = ToDoItemNetworkDto(
                    done = true,
                    created_at = LocalDate.now().toEpochMilli(),
                    text = "My task",
                    changed_at = LocalDate.now().toEpochMilli(),
                    id = "1",
                    importance = "basic",
                    last_updated_by = "null",
                    color = null,
                    deadline = null
                )
            )
            val empty = ToDoItemModel(
                id = "2",
                task = "task",
                priority = Priority.REGULAR,
                isDone = true,
                creationDate = LocalDate.now()
            )
            coEvery { todoItemApi.getToDoItem("1") } returns Response.success(dto)
            val expected = dto.toToDoItemModel()

            val actual = sut.getOrCreateItem("1", empty)

            assertThat(actual).isEqualTo(Resource.Success(expected))
        }

    @Test
    fun `given id and empty toDoItemModel should return Resource-Success(toDoItemModel) of empty model when not item exists`() =
        runTest {
            val dto = ToDoItemElementNetworkDto(
                status = "ok",
                revision = 1,
                element = ToDoItemNetworkDto(
                    done = true,
                    created_at = LocalDate.now().toEpochMilli(),
                    text = "My task",
                    changed_at = LocalDate.now().toEpochMilli(),
                    id = "1",
                    importance = "basic",
                    last_updated_by = "null",
                    color = null,
                    deadline = null
                )
            )
            val empty = ToDoItemModel(
                id = "2",
                task = "task",
                priority = Priority.REGULAR,
                isDone = true,
                creationDate = LocalDate.now()
            )
            coEvery { todoItemApi.getToDoItem("1") } returns Response.error(
                404,
                "Not Found".toResponseBody()
            )
            val expected = empty

            val actual = sut.getOrCreateItem("1", empty)

            assertThat(actual).isEqualTo(Resource.Success(expected))
        }

    @Test
    fun `given id and empty toDoItemModel should return Resource-Error(message) when api throw error`() =
        runTest {
            val empty = ToDoItemModel(
                id = "2",
                task = "task",
                priority = Priority.REGULAR,
                isDone = true,
                creationDate = LocalDate.now()
            )
            coEvery { todoItemApi.getToDoItem("1") } throws Exception(GET_ERROR)
            val expected = Resource.Error<ToDoItemModel>(GET_ERROR)

            val actual = sut.getOrCreateItem("1", empty)

            assertThat(actual).isEqualTo(expected)
        }
}