package com.arekalov.yandexshmr.presentation.edit

import app.cash.turbine.test
import com.arekalov.yandexshmr.data.common.GET_ERROR
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import com.arekalov.yandexshmr.domain.util.Resource
import com.arekalov.yandexshmr.presentation.edit.models.EditIntent
import com.arekalov.yandexshmr.presentation.edit.models.EditViewState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import com.arekalov.yandexshmr.presentation.common.models.Error as ErrorDataClass

class EditViewModelTest {
    private val repository = mockk<ToDoItemRepository>()
    private lateinit var sut: EditViewModel

    @BeforeEach
    fun setUp() {
        sut = EditViewModel(repository)
    }

    @Test
    fun `given initState intent should display it in state when item with current id exists`() =
        runTest {
            val item = ToDoItemModel(
                id = "1",
                task = "task item",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            coEvery { repository.getOrCreateItem(id = item.id) } returns Resource.Success(item)
            val expected = EditViewState.Display(
                item = item,
                navigateToHome = false
            )
            sut.editViewState.test {
                assertThat(awaitItem()).isEqualTo(EditViewState.Loading(navigateToHome = false))
                sut.obtainIntent(EditIntent.InitState(item.id))
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }

    @Test
    fun `given initState intent should display error state when repository returns error`() =
        runTest {
            val itemId = "1"
            coEvery { repository.getOrCreateItem(id = itemId) } returns Resource.Error(GET_ERROR)

            val emptyItem = ToDoItemModel(
                id = "empty",
                task = "",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val expectedState = EditViewState.Display(
                item = emptyItem,
                error = ErrorDataClass(
                    errorText = GET_ERROR,
                    onActionClick = { id: String -> sut.obtainIntent(EditIntent.InitState(id)) }
                ),
                navigateToHome = false
            )
            coEvery { repository.getEmptyToDoItemModel() } returns emptyItem

            sut.editViewState.test {
                assertThat(awaitItem()).isEqualTo(EditViewState.Loading(navigateToHome = false))
                sut.obtainIntent(EditIntent.InitState(itemId))
                val actualState = awaitItem() as EditViewState.Display
                assertThat(actualState.item).isEqualTo(expectedState.item)
                assertThat(actualState.navigateToHome).isEqualTo(expectedState.navigateToHome)
                assertThat(actualState.error?.errorText).isEqualTo(expectedState.error?.errorText)
                assertThat(actualState.error?.actionText).isEqualTo(expectedState.error?.actionText)
            }
        }

    @Test
    fun `given initState intent should display error when resource-getOrCreateItem throw Exception`() =
        runTest {
            val itemId = "1"
            coEvery { repository.getOrCreateItem(id = itemId) } throws Exception("error")

            val emptyItem = ToDoItemModel(
                id = "empty",
                task = "",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            val expectedState = EditViewState.Display(
                item = emptyItem,
                error = ErrorDataClass(
                    errorText = "error",
                    onActionClick = { },
                    actionText = null
                ),
                navigateToHome = false
            )
            coEvery { repository.getEmptyToDoItemModel() } returns emptyItem

            sut.editViewState.test {
                assertThat(awaitItem()).isEqualTo(EditViewState.Loading(navigateToHome = false))
                sut.obtainIntent(EditIntent.InitState(itemId))
                val actualState = awaitItem() as EditViewState.Display
                assertThat(actualState.item).isEqualTo(expectedState.item)
                assertThat(actualState.navigateToHome).isEqualTo(expectedState.navigateToHome)
                assertThat(actualState.error?.errorText).isEqualTo(expectedState.error?.errorText)
                assertThat(actualState.error?.actionText).isEqualTo(expectedState.error?.actionText)
            }
        }

    @Test
    fun `given 2 initState intent should display error when resource-getOrCreateItem throw Exception second time`() =
        runTest {
            val item = ToDoItemModel(
                id = "1",
                task = "task item",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )

            val expectedFirstState = EditViewState.Display(
                item = item,
                navigateToHome = false
            )
            val emptyItem = ToDoItemModel(
                id = "empty",
                task = "",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )

            val expectedErrorState = ErrorDataClass(
                errorText = "error",
                onActionClick = {},
                actionText = null
            )

            val expectedSecondState = EditViewState.Display(
                item = item,
                error = expectedErrorState,
                navigateToHome = false
            )

            coEvery { repository.getOrCreateItem(id = item.id) } returns Resource.Success(item)
            coEvery { repository.getOrCreateItem(id = "2") } throws Exception("error")
            coEvery { repository.getEmptyToDoItemModel() } returns emptyItem

            sut.editViewState.test {
                sut.obtainIntent(EditIntent.InitState(item.id))
                assertThat(awaitItem()).isEqualTo(EditViewState.Loading(navigateToHome = false))
                assertThat(awaitItem()).isEqualTo(expectedFirstState)
                sut.obtainIntent(EditIntent.InitState("2"))
                val actualState = awaitItem() as EditViewState.Display
                assertThat(actualState.item).isEqualTo(expectedSecondState.item)
                assertThat(actualState.navigateToHome).isEqualTo(expectedSecondState.navigateToHome)
                assertThat(actualState.error?.errorText).isEqualTo(expectedSecondState.error?.errorText)
                assertThat(actualState.error?.actionText).isEqualTo(expectedSecondState.error?.actionText)
            }
        }

    @Test
    fun `given id and toDoItem should save it on db when item exists in db and server`() =
        runTest {
            val item = ToDoItemModel(
                id = "1",
                task = "task item",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            coEvery { repository.getOrCreateItem(id = item.id) } returns Resource.Success(item)
            coEvery { repository.updateOrAddItem(item.id, item) } returns Resource.Success(item)

            val expectedStateAfterInit = EditViewState.Display(
                item = item,
                navigateToHome = false
            )

            val expectedStateAfterSave = EditViewState.Loading(
                navigateToHome = true
            )

            sut.editViewState.test {
                sut.obtainIntent(EditIntent.InitState(item.id))
                assertThat(awaitItem()).isEqualTo(EditViewState.Loading(navigateToHome = false))
                assertThat(awaitItem()).isEqualTo(expectedStateAfterInit)

                sut.obtainIntent(EditIntent.OnSaveCLick)
                assertThat(awaitItem()).isEqualTo(expectedStateAfterSave)

                coVerify { repository.updateOrAddItem(item.id, item) }
            }
        }

    @Test
    fun `given id and toDoItem should add error to state when getOrCreate item return Resource-Error`() =
        runTest {
            val item = ToDoItemModel(
                id = "1",
                task = "task item",
                priority = Priority.LOW,
                isDone = false,
                creationDate = LocalDate.now()
            )
            coEvery { repository.getOrCreateItem(id = item.id) } returns Resource.Success(item)
            coEvery { repository.updateOrAddItem(item.id, item) } returns Resource.Error(GET_ERROR)

            val expectedStateAfterInit = EditViewState.Display(
                item = item,
                navigateToHome = false
            )

            val expectedStateAfterSave = EditViewState.Display(
                navigateToHome = false,
                item = item,
                error = ErrorDataClass(
                    errorText = GET_ERROR,
                    onActionClick = { }
                )
            )

            sut.editViewState.test {
                sut.obtainIntent(EditIntent.InitState(item.id))
                assertThat(awaitItem()).isEqualTo(EditViewState.Loading(navigateToHome = false))
                assertThat(awaitItem()).isEqualTo(expectedStateAfterInit)

                sut.obtainIntent(EditIntent.OnSaveCLick)
                val state = awaitItem() as EditViewState.Display
                assertThat(state.item).isEqualTo(expectedStateAfterSave.item)
                assertThat(state.navigateToHome).isEqualTo(expectedStateAfterSave.navigateToHome)
                assertThat(state.error?.errorText).isEqualTo(expectedStateAfterSave.error?.errorText)

                coVerify { repository.updateOrAddItem(item.id, item) }
            }
        }
}