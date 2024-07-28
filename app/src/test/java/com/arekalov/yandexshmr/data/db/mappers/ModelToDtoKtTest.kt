package com.arekalov.yandexshmr.data.db.mappers

import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ModelToDtoKtTest {

    @Test
    fun `toToDoItemElementDbDto should correctly convert all fields`() {
        val model = ToDoItemModel(
            id = "1",
            task = "Test Task",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2021, 6, 1),
            isDone = true,
            creationDate = LocalDate.of(2021, 5, 31),
            editDate = LocalDate.of(2021, 7, 1)
        )

        val dbDto = model.toToDoItemElementDbDto()

        assertThat(dbDto.id).isEqualTo("1")
        assertThat(dbDto.itemType).isEqualTo("")
        assertThat(dbDto.task).isEqualTo("Test Task")
        assertThat(dbDto.voicePath).isNull()
        assertThat(dbDto.priority).isEqualTo(Priority.HIGH)
        assertThat(dbDto.deadline).isEqualTo("2021-06-01")
        assertThat(dbDto.isDone).isEqualTo(true)
        assertThat(dbDto.creationDate).isEqualTo("2021-05-31")
        assertThat(dbDto.editDate).isEqualTo("2021-07-01")
    }

    @Test
    fun `toToDoItemElementDbDto should handle null deadline and editDate`() {
        val model = ToDoItemModel(
            id = "2",
            task = "Another Task",
            priority = Priority.LOW,
            deadline = null,
            isDone = false,
            creationDate = LocalDate.of(2021, 5, 31),
            editDate = null
        )

        val dbDto = model.toToDoItemElementDbDto()

        assertThat(dbDto.id).isEqualTo("2")
        assertThat(dbDto.itemType).isEqualTo("")
        assertThat(dbDto.task).isEqualTo("Another Task")
        assertThat(dbDto.voicePath).isNull()
        assertThat(dbDto.priority).isEqualTo(Priority.LOW)
        assertThat(dbDto.deadline).isNull()
        assertThat(dbDto.isDone).isEqualTo(false)
        assertThat(dbDto.creationDate).isEqualTo("2021-05-31")
        assertThat(dbDto.editDate).isNull()
    }
}