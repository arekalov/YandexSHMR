package com.arekalov.yandexshmr.data.network.mappers

import com.arekalov.yandexshmr.data.network.dto.ToDoItemListNetworkDto
import com.arekalov.yandexshmr.data.network.dto.ToDoItemNetworkDto
import com.arekalov.yandexshmr.domain.model.Priority
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate


class DtoToModelMappersTest {
    @ParameterizedTest
    @ValueSource(strings = ["important", "imPoRTant"])
    fun `given important variant string should return Priority-High`(string: String) = runTest {
        val expected = Priority.HIGH
        val actual = string.toPriority()
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(strings = ["basiC", "other"])
    fun `given basic or other variant string should return Priority-Regular`(string: String) =
        runTest {
            val expected = Priority.REGULAR
            val actual = string.toPriority()
            assertThat(actual).isEqualTo(expected)
        }

    @ParameterizedTest
    @ValueSource(strings = ["low", "LoW"])
    fun `given low variant string should return Priority-High`(string: String) = runTest {
        val expected = Priority.LOW
        val actual = string.toPriority()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `toToDoItemModel should correctly convert all fields`() {
        val dto = ToDoItemNetworkDto(
            id = "1",
            text = "Test Task",
            importance = "important",
            deadline = 1622505600000, // 1 June 2021
            done = true,
            color = "#FFFFFF",
            created_at = 1622419200000, // 31 May 2021
            changed_at = 1625097600000, // 1 July 2021
            last_updated_by = "user1"
        )

        val model = dto.toToDoItemModel()

        assertThat(model.id).isEqualTo("1")
        assertThat(model.task).isEqualTo("Test Task")
        assertThat(model.priority).isEqualTo(Priority.HIGH)
        assertThat(model.deadline).isEqualTo(LocalDate.of(2021, 6, 1))
        assertThat(model.isDone).isEqualTo(true)
        assertThat(model.creationDate).isEqualTo(LocalDate.of(2021, 5, 31))
        assertThat(model.editDate).isEqualTo(LocalDate.of(2021, 7, 1))
    }

    @Test
    fun `toToDoItemModel should handle null deadline`() {
        val dto = ToDoItemNetworkDto(
            id = "1",
            text = "Test Task",
            importance = "regular",
            deadline = null,
            done = false,
            color = "#FFFFFF",
            created_at = 1622419200000, // 31 May 2021
            changed_at = 1622419200000, // 31 May 2021
            last_updated_by = "user1"
        )

        val model = dto.toToDoItemModel()

        assertThat(model.deadline).isNull()
    }

    @Test
    fun `toToDoItemModel should handle null editDate when changed_at equals created_at`() {
        val dto = ToDoItemNetworkDto(
            id = "1",
            text = "Test Task",
            importance = "regular",
            deadline = 1622505600000, // 1 June 2021
            done = false,
            color = "#FFFFFF",
            created_at = 1622419200000, // 31 May 2021
            changed_at = 1622419200000, // 31 May 2021
            last_updated_by = "user1"
        )

        val model = dto.toToDoItemModel()

        assertThat(model.editDate).isNull()
    }

    @Test
    fun `toToDoItemModel should handle editDate when changed_at not equals created_at`() {
        val dto = ToDoItemNetworkDto(
            id = "1",
            text = "Test Task",
            importance = "regular",
            deadline = 1622505600000, // 1 June 2021
            done = false,
            color = "#FFFFFF",
            created_at = 1622419200000, // 31 May 2021
            changed_at = 1625097600000, // 1 July 2021
            last_updated_by = "user1"
        )

        val model = dto.toToDoItemModel()

        assertThat(model.editDate).isEqualTo(LocalDate.of(2021, 7, 1))
    }

    @Test
    fun `toToDoItemListModel should correctly convert all items`() {
        val dtoList = ToDoItemListNetworkDto(
            status = "success",
            list = listOf(
                ToDoItemNetworkDto(
                    id = "1",
                    text = "Task 1",
                    importance = "important",
                    deadline = 1622505600000, // 1 June 2021
                    done = true,
                    color = "#FFFFFF",
                    created_at = 1622419200000, // 31 May 2021
                    changed_at = 1625097600000, // 1 July 2021
                    last_updated_by = "user1"
                ),
                ToDoItemNetworkDto(
                    id = "2",
                    text = "Task 2",
                    importance = "low",
                    deadline = null,
                    done = false,
                    color = "#FFFFFF",
                    created_at = 1622419200000, // 31 May 2021
                    changed_at = 1622419200000, // 31 May 2021
                    last_updated_by = "user2"
                )
            ),
            revision = 1
        )

        val modelList = dtoList.toToDoItemListModel()

        assertThat(modelList).hasSize(2)

        val model1 = modelList[0]
        assertThat(model1.id).isEqualTo("1")
        assertThat(model1.task).isEqualTo("Task 1")
        assertThat(model1.priority).isEqualTo(Priority.HIGH)
        assertThat(model1.deadline).isEqualTo(LocalDate.of(2021, 6, 1))
        assertThat(model1.isDone).isEqualTo(true)
        assertThat(model1.creationDate).isEqualTo(LocalDate.of(2021, 5, 31))
        assertThat(model1.editDate).isEqualTo(LocalDate.of(2021, 7, 1))

        val model2 = modelList[1]
        assertThat(model2.id).isEqualTo("2")
        assertThat(model2.task).isEqualTo("Task 2")
        assertThat(model2.priority).isEqualTo(Priority.LOW)
        assertThat(model2.deadline).isNull()
        assertThat(model2.isDone).isEqualTo(false)
        assertThat(model2.creationDate).isEqualTo(LocalDate.of(2021, 5, 31))
        assertThat(model2.editDate).isNull()
    }

    @Test
    fun `toToDoItemListModel should handle empty list`() {
        val dtoList = ToDoItemListNetworkDto(
            status = "success",
            list = emptyList(),
            revision = 1
        )

        val modelList = dtoList.toToDoItemListModel()

        assertThat(modelList).isEmpty()
    }
}
