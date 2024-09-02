package com.arekalov.yandexshmr.data.db.mappers

import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto
import com.arekalov.yandexshmr.domain.model.Priority
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DtoToModelKtTest {
    @Test
    fun `given valid ToDoItemElementDbDto should map to ToDoItemModel correctly`() {
        // Given
        val dto = ToDoItemElementDbDto(
            id = "1",
            itemType = "TEXT",
            task = "Test task",
            voicePath = null,
            priority = Priority.HIGH,
            deadline = "2024-12-31",
            isDone = false,
            creationDate = "2024-07-25",
            editDate = "2024-07-26"
        )

        // When
        val model = dto.toToDoItemModel()

        // Then
        assertEquals("1", model.id)
        assertEquals("Test task", model.task)
        assertEquals(Priority.HIGH, model.priority)
        assertEquals(LocalDate.parse("2024-12-31"), model.deadline)
        assertEquals(false, model.isDone)
        assertEquals(LocalDate.parse("2024-07-25"), model.creationDate)
        assertEquals(LocalDate.parse("2024-07-26"), model.editDate)
    }

    @Test
    fun `given ToDoItemElementDbDto with null deadline and editDate should map to ToDoItemModel correctly`() {
        // Given
        val dto = ToDoItemElementDbDto(
            id = "2",
            itemType = "TEXT",
            task = "Test task",
            voicePath = null,
            priority = Priority.REGULAR,
            deadline = null,
            isDone = true,
            creationDate = "2024-07-25",
            editDate = null
        )

        // When
        val model = dto.toToDoItemModel()

        // Then
        assertEquals("2", model.id)
        assertEquals("Test task", model.task)
        assertEquals(Priority.REGULAR, model.priority)
        assertEquals(null, model.deadline)
        assertEquals(true, model.isDone)
        assertEquals(LocalDate.parse("2024-07-25"), model.creationDate)
        assertEquals(null, model.editDate)
    }

    @Test
    fun `given ToDoItemElementDbDto with null task should map to ToDoItemModel with empty task`() {
        // Given
        val dto = ToDoItemElementDbDto(
            id = "3",
            itemType = "TEXT",
            task = null,
            voicePath = null,
            priority = Priority.LOW,
            deadline = null,
            isDone = false,
            creationDate = "2024-07-25",
            editDate = null
        )

        // When
        val model = dto.toToDoItemModel()

        // Then
        assertEquals("3", model.id)
        assertEquals("", model.task)  // task should be mapped to an empty string
        assertEquals(Priority.LOW, model.priority)
        assertEquals(null, model.deadline)
        assertEquals(false, model.isDone)
        assertEquals(LocalDate.parse("2024-07-25"), model.creationDate)
        assertEquals(null, model.editDate)
    }
}