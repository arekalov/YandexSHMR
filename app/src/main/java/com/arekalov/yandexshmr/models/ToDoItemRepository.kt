package com.arekalov.yandexshmr.models

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class ToDoItemRepository() {
    private val itemList = mutableListOf<ToDoItem>() // Ваш список данных
    private val _itemListFlow = MutableStateFlow<List<ToDoItem>>(itemList)
    val itemListFlow: StateFlow<List<ToDoItem>>
        get() = _itemListFlow

    init {
        val todoItemsToAdd = listOf(
            ToDoItem(
                mutableStateOf("id1"),
                mutableStateOf("Подготовка презентацию для встречи с очень очень очень очень очень очень очень очень очень очень очень очень длинным текстом "),
                mutableStateOf(Priority.HIGH),
                mutableStateOf<LocalDate?>(null),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 15)),
                mutableStateOf(LocalDate.of(2024, 7, 14))
            ),
            ToDoItem(
                mutableStateOf("id2"),
                mutableStateOf("Купить продукты в супермаркете"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 7, 20)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 12)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id3"),
                mutableStateOf("Выучить новый язык программирования"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 7, 25)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 5)),
                mutableStateOf(LocalDate.of(2024, 7, 20))
            ),
            ToDoItem(
                mutableStateOf("id4"),
                mutableStateOf("Сделать зарядку каждое утро"),
                mutableStateOf(Priority.LOW),
                mutableStateOf<LocalDate?>(null),
                mutableStateOf(true),
                mutableStateOf(LocalDate.of(2024, 7, 8)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id5"),
                mutableStateOf("Подготовиться к экзамену по математике"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf<LocalDate?>(null),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 6, 25)),
                mutableStateOf(LocalDate.of(2024, 7, 28))
            ),
            ToDoItem(
                mutableStateOf("id6"),
                mutableStateOf("Прочитать новую книгу"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 8, 5)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 1)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id7"),
                mutableStateOf("Сходить к врачу на осмотр"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 8, 10)),
                mutableStateOf(true),
                mutableStateOf(LocalDate.of(2024, 7, 3)),
                mutableStateOf(LocalDate.of(2024, 8, 9))
            ),
            ToDoItem(
                mutableStateOf("id8"),
                mutableStateOf("Написать отчет о выполненной работе"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 8, 15)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 7)),
                mutableStateOf(LocalDate.of(2024, 8, 14))
            ),
            ToDoItem(
                mutableStateOf("id9"),
                mutableStateOf("Провести ремонт в квартире"),
                mutableStateOf(Priority.LOW),
                mutableStateOf(LocalDate.of(2024, 8, 20)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 20)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id10"),
                mutableStateOf("Заказать подарок на день рождения друга"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 8, 25)),
                mutableStateOf(true),
                mutableStateOf(LocalDate.of(2024, 7, 15)),
                mutableStateOf(LocalDate.of(2024, 8, 22))
            ),
            ToDoItem(
                mutableStateOf("id11"),
                mutableStateOf("Планирование отпуска на следующий год"),
                mutableStateOf(Priority.LOW),
                mutableStateOf(LocalDate.of(2024, 9, 1)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 30)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id12"),
                mutableStateOf("Заняться спортом три раза в неделю"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 9, 5)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 8, 1)),
                mutableStateOf(LocalDate.of(2024, 9, 3))
            ),
            ToDoItem(
                mutableStateOf("id13"),
                mutableStateOf("Подготовиться к интервью на новую работу"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 9, 10)),
                mutableStateOf(true),
                mutableStateOf(LocalDate.of(2024, 8, 3)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id14"),
                mutableStateOf("Провести вечеринку в выходной день"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 9, 15)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 8, 5)),
                mutableStateOf(LocalDate.of(2024, 9, 14))
            ),
            ToDoItem(
                mutableStateOf("id15"),
                mutableStateOf("Найти и оформить новое жилье"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 9, 20)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 8, 10)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id16"),
                mutableStateOf("Купить продукты в супермаркете"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 7, 20)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 12)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id17"),
                mutableStateOf("Выучить новый язык программирования"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 7, 25)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 5)),
                mutableStateOf(LocalDate.of(2024, 7, 20))
            ),
            ToDoItem(
                mutableStateOf("id18"),
                mutableStateOf("Сделать зарядку каждое утро"),
                mutableStateOf(Priority.LOW),
                mutableStateOf<LocalDate?>(null),
                mutableStateOf(true),
                mutableStateOf(LocalDate.of(2024, 7, 8)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id19"),
                mutableStateOf("Подготовиться к экзамену по математике"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 7, 30)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 6, 25)),
                mutableStateOf(LocalDate.of(2024, 7, 28))
            ),
            ToDoItem(
                mutableStateOf("id20"),
                mutableStateOf("Прочитать новую книгу"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 8, 5)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 1)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id21"),
                mutableStateOf("Сходить к врачу на осмотр"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 8, 10)),
                mutableStateOf(true),
                mutableStateOf(LocalDate.of(2024, 7, 3)),
                mutableStateOf(LocalDate.of(2024, 8, 9))
            ),
            ToDoItem(
                mutableStateOf("id22"),
                mutableStateOf("Написать отчет о выполненной работе"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 8, 15)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 7)),
                mutableStateOf(LocalDate.of(2024, 8, 14))
            ),
            ToDoItem(
                mutableStateOf("id23"),
                mutableStateOf("Провести ремонт в квартире"),
                mutableStateOf(Priority.LOW),
                mutableStateOf(LocalDate.of(2024, 8, 20)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 20)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id24"),
                mutableStateOf("Заказать подарок на день рождения друга"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 8, 25)),
                mutableStateOf(true),
                mutableStateOf(LocalDate.of(2024, 7, 15)),
                mutableStateOf(LocalDate.of(2024, 8, 22))
            ),
            ToDoItem(
                mutableStateOf("id25"),
                mutableStateOf("Планирование отпуска на следующий год"),
                mutableStateOf(Priority.LOW),
                mutableStateOf(LocalDate.of(2024, 9, 1)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 7, 30)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id26"),
                mutableStateOf("Заняться спортом три раза в неделю"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 9, 5)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 8, 1)),
                mutableStateOf(LocalDate.of(2024, 9, 3))
            ),
            ToDoItem(
                mutableStateOf("id27"),
                mutableStateOf("Подготовиться к интервью на новую работу"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 9, 10)),
                mutableStateOf(true),
                mutableStateOf(LocalDate.of(2024, 8, 3)),
                mutableStateOf<LocalDate?>(null)
            ),
            ToDoItem(
                mutableStateOf("id28"),
                mutableStateOf("Провести вечеринку в выходной день"),
                mutableStateOf(Priority.REGULAR),
                mutableStateOf(LocalDate.of(2024, 9, 15)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 8, 5)),
                mutableStateOf(LocalDate.of(2024, 9, 14))
            ),
            ToDoItem(
                mutableStateOf("id29"),
                mutableStateOf("Найти и оформить новое жилье"),
                mutableStateOf(Priority.HIGH),
                mutableStateOf(LocalDate.of(2024, 9, 20)),
                mutableStateOf(false),
                mutableStateOf(LocalDate.of(2024, 8, 10)),
                mutableStateOf<LocalDate?>(null)
            )

        )
        itemList.addAll(todoItemsToAdd)
        _itemListFlow.value = itemList
    }

    fun deleteItem(item: ToDoItem) {
        itemList.remove(item)
        _itemListFlow.value = itemList
    }

    fun updateItem(id: String, item: ToDoItem) {
        val foundItem = itemList.find { it.id.value == id }
        val index = itemList.indexOf(foundItem)
        itemList[index] = item
        _itemListFlow.value = itemList
    }

    fun addItem(item: ToDoItem) {
        itemList.add(item)
        _itemListFlow.value = itemList
    }
}