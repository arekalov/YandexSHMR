package com.arekalov.yandexshmr.data.common

import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ToDoItemDtoToModelKtTest {
    @Test
    fun `given empty list should return empty ToDoItemListModel when mapping`() {

        val emptyList = emptyList<ToDoItemModel>()


        val result = mapToDoItemModelToListItemModel(emptyList)


        assertEquals(emptyMap<String, ToDoItemModel>(), result.itemsMap)
        assertEquals(emptyList, result.items)
        assertEquals(0, result.doneCount)
    }

    @Test
    fun `given list with one not done item should return correct ToDoItemListModel when mapping`() {

        val toDoItem = ToDoItemModel(
            id = "1",
            task = "task 1",
            priority = Priority.LOW,
            isDone = false,
            creationDate = LocalDate.now()
        )
        val itemList = listOf(toDoItem)


        val result = mapToDoItemModelToListItemModel(itemList)


        assertEquals(mapOf("1" to toDoItem), result.itemsMap)
        assertEquals(itemList, result.items)
        assertEquals(0, result.doneCount)
    }

    @Test
    fun `given list with one done item should return correct ToDoItemListModel when mapping`() {

        val toDoItem = ToDoItemModel(
            id = "1",
            task = "task 1",
            priority = Priority.LOW,
            isDone = true,
            creationDate = LocalDate.now()
        )
        val itemList = listOf(toDoItem)


        val result = mapToDoItemModelToListItemModel(itemList)


        assertEquals(mapOf("1" to toDoItem), result.itemsMap)
        assertEquals(itemList, result.items)
        assertEquals(1, result.doneCount)
    }

    @Test
    fun `given list with mixed done and not done items should return correct ToDoItemListModel when mapping`() {
        val toDoItem1 = ToDoItemModel(
            id = "1",
            task = "task 1",
            priority = Priority.LOW,
            isDone = true,
            creationDate = LocalDate.now()
        )
        val toDoItem2 = ToDoItemModel(
            id = "2",
            task = "task 2",
            priority = Priority.HIGH,
            isDone = false,
            creationDate = LocalDate.now()
        )
        val itemList = listOf(toDoItem1, toDoItem2)

        val result = mapToDoItemModelToListItemModel(itemList)

        assertEquals(mapOf("1" to toDoItem1, "2" to toDoItem2), result.itemsMap)
        assertEquals(itemList, result.items)
        assertEquals(1, result.doneCount)
    }


}