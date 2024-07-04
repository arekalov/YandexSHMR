package com.arekalov.yandexshmr.data.repository

import com.arekalov.yandexshmr.data.dto.Priority
import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.data.mappers.mapToDoItemsToModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

internal class ToDoItemRepositoryImpl : ToDoItemRepository {
    val itemsList = listOf(
        ToDoItemDto(
            id = "id1",
            task = "Подготовка презентацию для встречи с очень очень очень очень очень очень очень" +
                    " очень очень очень очень очень длинным текстом",
            priority = Priority.HIGH,
            deadline = null,
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 15),
            editDate = LocalDate.of(2024, 7, 14)
        ),
        ToDoItemDto(
            id = "id2",
            task = "Купить продукты в супермаркете",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 7, 20),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 12),
            editDate = null
        ),
        ToDoItemDto(
            id = "id3",
            task = "Выучить новый язык программирования",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2024, 7, 25),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 5),
            editDate = LocalDate.of(2024, 7, 20)
        ),
        ToDoItemDto(
            id = "id4",
            task = "Сделать зарядку каждое утро",
            priority = Priority.LOW,
            deadline = null,
            isDone = true,
            creationDate = LocalDate.of(2024, 7, 8),
            editDate = null
        ),
        ToDoItemDto(
            id = "id5",
            task = "Подготовиться к экзамену по математике",
            priority = Priority.HIGH,
            deadline = null,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 25),
            editDate = LocalDate.of(2024, 7, 28)
        ),
        ToDoItemDto(
            id = "id6",
            task = "Прочитать новую книгу",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 8, 5),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 1),
            editDate = null
        ),
        ToDoItemDto(
            id = "id7",
            task = "Сходить к врачу на осмотр",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2024, 8, 10),
            isDone = true,
            creationDate = LocalDate.of(2024, 7, 3),
            editDate = LocalDate.of(2024, 8, 9)
        ),
        ToDoItemDto(
            id = "id8",
            task = "Написать отчет о выполненной работе",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 8, 15),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 7),
            editDate = LocalDate.of(2024, 8, 14)
        ),
        ToDoItemDto(
            id = "id9",
            task = "Провести ремонт в квартире",
            priority = Priority.LOW,
            deadline = LocalDate.of(2024, 8, 20),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 20),
            editDate = null
        ),
        ToDoItemDto(
            id = "id10",
            task = "Заказать подарок на день рождения друга",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 8, 25),
            isDone = true,
            creationDate = LocalDate.of(2024, 7, 15),
            editDate = LocalDate.of(2024, 8, 22)
        ),
        ToDoItemDto(
            id = "id11",
            task = "Планирование отпуска на следующий год",
            priority = Priority.LOW,
            deadline = LocalDate.of(2024, 9, 1),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 30),
            editDate = null
        ),
        ToDoItemDto(
            id = "id12",
            task = "Заняться спортом три раза в неделю",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 9, 5),
            isDone = false,
            creationDate = LocalDate.of(2024, 8, 1),
            editDate = LocalDate.of(2024, 9, 3)
        ),
        ToDoItemDto(
            id = "id13",
            task = "Подготовиться к интервью на новую работу",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2024, 9, 10),
            isDone = true,
            creationDate = LocalDate.of(2024, 8, 3),
            editDate = null
        ),
        ToDoItemDto(
            id = "id14",
            task = "Провести вечеринку в выходной день",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 9, 15),
            isDone = false,
            creationDate = LocalDate.of(2024, 8, 5),
            editDate = LocalDate.of(2024, 9, 14)
        ),
        ToDoItemDto(
            id = "id15",
            task = "Найти и оформить новое жилье",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2024, 9, 20),
            isDone = false,
            creationDate = LocalDate.of(2024, 8, 10),
            editDate = null
        )
    )

    private val _todoItems = MutableStateFlow(mapToDoItemsToModel(itemsList))
    override val todoItems: Flow<ToDoItemModel>
        get() = _todoItems

    override fun addTodoItem(item: ToDoItemDto) {
        _todoItems.update { currentModel ->
            val updatedItemsMap = currentModel.itemsMap.toMutableMap()
            updatedItemsMap[item.id] = item
            val updatedList = updatedItemsMap.values.toList()
            mapToDoItemsToModel(updatedList)
        }
    }

    override fun updateTodoItem(id: String, itemToUpdate: ToDoItemDto) {
        _todoItems.update { currentModel ->
            val updatedItemsMap = currentModel.itemsMap.toMutableMap()
            if (updatedItemsMap.containsKey(id)) {
                updatedItemsMap[id] = itemToUpdate
            }
            val updatedList = updatedItemsMap.values.toList()
            mapToDoItemsToModel(updatedList)
        }
    }

    override fun deleteTodoItem(id: String) {
        _todoItems.update { currentModel ->
            val updatedItemsMap = currentModel.itemsMap.toMutableMap()
            updatedItemsMap.remove(id)
            val updatedList = updatedItemsMap.values.toList()
            mapToDoItemsToModel(updatedList)
        }
    }
}