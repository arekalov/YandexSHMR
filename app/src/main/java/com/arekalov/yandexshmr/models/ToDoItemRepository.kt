package com.arekalov.yandexshmr.models

import java.time.LocalDate

class ToDoItemRepository() {
    private val _todoItems = mutableListOf<ToDoItem>()
    val todoItems: List<ToDoItem> = _todoItems

    init {
        val todoItemsToAdd = listOf(
            ToDoItem(
                "id1",
                "Подготовить презентацию для встречи",
                Priority.HIGH,
                LocalDate.of(2024, 7, 15),
                true,
                LocalDate.of(2024, 7, 15),
                LocalDate.of(2024, 7, 14)
            ),
            ToDoItem(
                "id2",
                "Купить продукты в супермаркете",
                Priority.REGULAR,
                LocalDate.of(2024, 7, 20),
                false,
                LocalDate.of(2024, 7, 12),
                null
            ),
            ToDoItem(
                "id3",
                "Выучить новый язык программирования",
                Priority.HIGH,
                LocalDate.of(2024, 7, 25),
                false,
                LocalDate.of(2024, 7, 5),
                LocalDate.of(2024, 7, 20)
            ),
            ToDoItem(
                "id4",
                "Сделать зарядку каждое утро",
                Priority.LOW,
                null,
                true,
                LocalDate.of(2024, 7, 8),
                null
            ),
            ToDoItem(
                "id5",
                "Подготовиться к экзамену по математике",
                Priority.HIGH,
                LocalDate.of(2024, 7, 30),
                false,
                LocalDate.of(2024, 6, 25),
                LocalDate.of(2024, 7, 28)
            ),
            ToDoItem(
                "id6",
                "Прочитать новую книгу",
                Priority.REGULAR,
                LocalDate.of(2024, 8, 5),
                false,
                LocalDate.of(2024, 7, 1),
                null
            ),
            ToDoItem(
                "id7",
                "Сходить к врачу на осмотр",
                Priority.HIGH,
                LocalDate.of(2024, 8, 10),
                true,
                LocalDate.of(2024, 7, 3),
                LocalDate.of(2024, 8, 9)
            ),
            ToDoItem(
                "id8",
                "Написать отчет о выполненной работе",
                Priority.REGULAR,
                LocalDate.of(2024, 8, 15),
                false,
                LocalDate.of(2024, 7, 7),
                LocalDate.of(2024, 8, 14)
            ),
            ToDoItem(
                "id9",
                "Провести ремонт в квартире",
                Priority.LOW,
                LocalDate.of(2024, 8, 20),
                false,
                LocalDate.of(2024, 7, 20),
                null
            ),
            ToDoItem(
                "id10",
                "Заказать подарок на день рождения друга",
                Priority.REGULAR,
                LocalDate.of(2024, 8, 25),
                true,
                LocalDate.of(2024, 7, 15),
                LocalDate.of(2024, 8, 22)
            ),
            ToDoItem(
                "id11",
                "Планирование отпуска на следующий год",
                Priority.LOW,
                LocalDate.of(2024, 9, 1),
                false,
                LocalDate.of(2024, 7, 30),
                null
            ),
            ToDoItem(
                "id12",
                "Заняться спортом три раза в неделю",
                Priority.REGULAR,
                LocalDate.of(2024, 9, 5),
                false,
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 9, 3)
            ),
            ToDoItem(
                "id13",
                "Подготовиться к интервью на новую работу",
                Priority.HIGH,
                LocalDate.of(2024, 9, 10),
                true,
                LocalDate.of(2024, 8, 3),
                null
            ),
            ToDoItem(
                "id14",
                "Провести вечеринку в выходной день",
                Priority.REGULAR,
                LocalDate.of(2024, 9, 15),
                false,
                LocalDate.of(2024, 8, 5),
                LocalDate.of(2024, 9, 14)
            ),
            ToDoItem(
                "id15",
                "Найти и оформить новое жилье",
                Priority.HIGH,
                LocalDate.of(2024, 9, 20),
                false,
                LocalDate.of(2024, 8, 10),
                null
            ),
        )
        _todoItems.addAll(todoItemsToAdd)
    }

    fun addItem(item: ToDoItem) {
        _todoItems.add(item)
    }
}