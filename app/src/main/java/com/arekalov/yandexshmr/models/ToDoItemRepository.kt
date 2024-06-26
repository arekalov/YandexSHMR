package com.arekalov.yandexshmr.models

import java.time.LocalDate

class ToDoItemRepository {
    val itemList = mutableListOf<ToDoItem>() // Ваш список данных

    init {
        val todoItemsToAdd = listOf(
            ToDoItem(
                id = "id1",
                task = "Подготовка презентацию для встречи с очень очень очень очень очень очень очень" +
                        " очень очень очень очень очень длинным текстом",
                priority = Priority.HIGH,
                deadline = null,
                isDone = false,
                creationDate = LocalDate.of(2024, 7, 15),
                editDate = LocalDate.of(2024, 7, 14)
            ),
            ToDoItem(
                id = "id2",
                task = "Купить продукты в супермаркете",
                priority = Priority.REGULAR,
                deadline = LocalDate.of(2024, 7, 20),
                isDone = false,
                creationDate = LocalDate.of(2024, 7, 12),
                editDate = null
            ),
            ToDoItem(
                id = "id3",
                task = "Выучить новый язык программирования",
                priority = Priority.HIGH,
                deadline = LocalDate.of(2024, 7, 25),
                isDone = false,
                creationDate = LocalDate.of(2024, 7, 5),
                editDate = LocalDate.of(2024, 7, 20)
            ),
            ToDoItem(
                id = "id4",
                task = "Сделать зарядку каждое утро",
                priority = Priority.LOW,
                deadline = null,
                isDone = true,
                creationDate = LocalDate.of(2024, 7, 8),
                editDate = null
            ),
            ToDoItem(
                id = "id5",
                task = "Подготовиться к экзамену по математике",
                priority = Priority.HIGH,
                deadline = null,
                isDone = false,
                creationDate = LocalDate.of(2024, 6, 25),
                editDate = LocalDate.of(2024, 7, 28)
            ),
            ToDoItem(
                id = "id6",
                task = "Прочитать новую книгу",
                priority = Priority.REGULAR,
                deadline = LocalDate.of(2024, 8, 5),
                isDone = false,
                creationDate = LocalDate.of(2024, 7, 1),
                editDate = null
            ),
            ToDoItem(
                id = "id7",
                task = "Сходить к врачу на осмотр",
                priority = Priority.HIGH,
                deadline = LocalDate.of(2024, 8, 10),
                isDone = true,
                creationDate = LocalDate.of(2024, 7, 3),
                editDate = LocalDate.of(2024, 8, 9)
            ),
            ToDoItem(
                id = "id8",
                task = "Написать отчет о выполненной работе",
                priority = Priority.REGULAR,
                deadline = LocalDate.of(2024, 8, 15),
                isDone = false,
                creationDate = LocalDate.of(2024, 7, 7),
                editDate = LocalDate.of(2024, 8, 14)
            ),
            ToDoItem(
                id = "id9",
                task = "Провести ремонт в квартире",
                priority = Priority.LOW,
                deadline = LocalDate.of(2024, 8, 20),
                isDone = false,
                creationDate = LocalDate.of(2024, 7, 20),
                editDate = null
            ),
            ToDoItem(
                id = "id10",
                task = "Заказать подарок на день рождения друга",
                priority = Priority.REGULAR,
                deadline = LocalDate.of(2024, 8, 25),
                isDone = true,
                creationDate = LocalDate.of(2024, 7, 15),
                editDate = LocalDate.of(2024, 8, 22)
            ),
            ToDoItem(
                id = "id11",
                task = "Планирование отпуска на следующий год",
                priority = Priority.LOW,
                deadline = LocalDate.of(2024, 9, 1),
                isDone = false,
                creationDate = LocalDate.of(2024, 7, 30),
                editDate = null
            ),
            ToDoItem(
                id = "id12",
                task = "Заняться спортом три раза в неделю",
                priority = Priority.REGULAR,
                deadline = LocalDate.of(2024, 9, 5),
                isDone = false,
                creationDate = LocalDate.of(2024, 8, 1),
                editDate = LocalDate.of(2024, 9, 3)
            ),
            ToDoItem(
                id = "id13",
                task = "Подготовиться к интервью на новую работу",
                priority = Priority.HIGH,
                deadline = LocalDate.of(2024, 9, 10),
                isDone = true,
                creationDate = LocalDate.of(2024, 8, 3),
                editDate = null
            ),
            ToDoItem(
                id = "id14",
                task = "Провести вечеринку в выходной день",
                priority = Priority.REGULAR,
                deadline = LocalDate.of(2024, 9, 15),
                isDone = false,
                creationDate = LocalDate.of(2024, 8, 5),
                editDate = LocalDate.of(2024, 9, 14)
            ),
            ToDoItem(
                id = "id15",
                task = "Найти и оформить новое жилье",
                priority = Priority.HIGH,
                deadline = LocalDate.of(2024, 9, 20),
                isDone = false,
                creationDate = LocalDate.of(2024, 8, 10),
                editDate = null
            )
        )
        itemList.addAll(todoItemsToAdd)
    }

    fun deleteItem(item: ToDoItem) {
        itemList.remove(item)
    }

    fun updateItem(id: String, item: ToDoItem) {
        val foundItem = itemList.find { it.id == id }
        val index = itemList.indexOf(foundItem)
        itemList[index] = item
    }

    fun addItem(item: ToDoItem) {
        itemList.add(item)
    }
}