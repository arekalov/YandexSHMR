package com.arekalov.yandexshmr.data.repository

import com.arekalov.yandexshmr.data.common.mapToDoItemModelToListItemModel
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemListModel
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID


/**
Fake repository with hardcode data for testing and previews
 **/
class FakeHardCodeToDoItemRepository { // временно public, после внедрения di станет internal
    val itemsList = listOf(
        ToDoItemModel(
            id = "id1",
            task = "Подготовка презентацию для встречи с очень очень очень очень очень очень очень" +
                    " очень очень очень очень очень длинным текстом",
            priority = Priority.HIGH,
            deadline = null,
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 15),
            editDate = LocalDate.of(2024, 7, 14)
        ),
        ToDoItemModel(
            id = "id2",
            task = "Купить продукты в супермаркете",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 7, 20),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 12),
            editDate = null
        ),
        ToDoItemModel(
            id = "id3",
            task = "Выучить новый язык программирования",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2024, 7, 25),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 5),
            editDate = LocalDate.of(2024, 7, 20)
        ),
        ToDoItemModel(
            id = "id4",
            task = "Сделать зарядку каждое утро",
            priority = Priority.LOW,
            deadline = null,
            isDone = true,
            creationDate = LocalDate.of(2024, 7, 8),
            editDate = null
        ),
        ToDoItemModel(
            id = "id5",
            task = "Подготовиться к экзамену по математике",
            priority = Priority.HIGH,
            deadline = null,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 25),
            editDate = LocalDate.of(2024, 7, 28)
        ),
        ToDoItemModel(
            id = "id6",
            task = "Прочитать новую книгу",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 8, 5),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 1),
            editDate = null
        ),
        ToDoItemModel(
            id = "id7",
            task = "Сходить к врачу на осмотр",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2024, 8, 10),
            isDone = true,
            creationDate = LocalDate.of(2024, 7, 3),
            editDate = LocalDate.of(2024, 8, 9)
        ),
        ToDoItemModel(
            id = "id8",
            task = "Написать отчет о выполненной работе",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 8, 15),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 7),
            editDate = LocalDate.of(2024, 8, 14)
        ),
        ToDoItemModel(
            id = "id9",
            task = "Провести ремонт в квартире",
            priority = Priority.LOW,
            deadline = LocalDate.of(2024, 8, 20),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 20),
            editDate = null
        ),
        ToDoItemModel(
            id = "id10",
            task = "Заказать подарок на день рождения друга",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 8, 25),
            isDone = true,
            creationDate = LocalDate.of(2024, 7, 15),
            editDate = LocalDate.of(2024, 8, 22)
        ),
        ToDoItemModel(
            id = "id11",
            task = "Планирование отпуска на следующий год",
            priority = Priority.LOW,
            deadline = LocalDate.of(2024, 9, 1),
            isDone = false,
            creationDate = LocalDate.of(2024, 7, 30),
            editDate = null
        ),
        ToDoItemModel(
            id = "id12",
            task = "Заняться спортом три раза в неделю",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 9, 5),
            isDone = false,
            creationDate = LocalDate.of(2024, 8, 1),
            editDate = LocalDate.of(2024, 9, 3)
        ),
        ToDoItemModel(
            id = "id13",
            task = "Подготовиться к интервью на новую работу",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2024, 9, 10),
            isDone = true,
            creationDate = LocalDate.of(2024, 8, 3),
            editDate = null
        ),
        ToDoItemModel(
            id = "id14",
            task = "Провести вечеринку в выходной день",
            priority = Priority.REGULAR,
            deadline = LocalDate.of(2024, 9, 15),
            isDone = false,
            creationDate = LocalDate.of(2024, 8, 5),
            editDate = LocalDate.of(2024, 9, 14)
        ),
        ToDoItemModel(
            id = "id15",
            task = "Найти и оформить новое жилье",
            priority = Priority.HIGH,
            deadline = LocalDate.of(2024, 9, 20),
            isDone = false,
            creationDate = LocalDate.of(2024, 8, 10),
            editDate = null
        )
    )

    private val _todoItems = MutableStateFlow(mapToDoItemModelToListItemModel(itemsList))
    val todoItems: Flow<ToDoItemListModel>
        get() = _todoItems

    suspend fun addTodoItem(item: ToDoItemModel) {
        _todoItems.update { currentModel ->
            val updatedItemsMap = currentModel.itemsMap.toMutableMap()
            updatedItemsMap[item.id] = item
            val updatedList = updatedItemsMap.values.toList()
            mapToDoItemModelToListItemModel(updatedList)
        }
    }

    suspend fun updateTodoItem(id: String, itemToUpdate: ToDoItemModel) {
        _todoItems.update { currentModel ->
            val updatedItemsMap = currentModel.itemsMap.toMutableMap()
            if (updatedItemsMap.containsKey(id)) {
                updatedItemsMap[id] = itemToUpdate
            }
            val updatedList = updatedItemsMap.values.toList()
            mapToDoItemModelToListItemModel(updatedList)
        }
    }

    suspend fun deleteTodoItem(id: String) {
        _todoItems.update { currentModel ->
            val updatedItemsMap = currentModel.itemsMap.toMutableMap()
            updatedItemsMap.remove(id)
            val updatedList = updatedItemsMap.values.toList()
            mapToDoItemModelToListItemModel(updatedList)
        }
    }

    suspend fun getOrCreateItem(id: String): ToDoItemModel {
        if (_todoItems.value.itemsMap.containsKey(id)) {
            return _todoItems.value.itemsMap[id]!!
        }
        val item = ToDoItemModel(
            id = UUID.randomUUID().toString(),
            task = "",
            priority = Priority.REGULAR,
            isDone = false,
            creationDate = LocalDate.now()
        )
        addTodoItem(item)
        return item
    }
}