package com.arekalov.yandexshmr.models

import java.time.LocalDate

data class ToDoItem(
    val id: String,
    val task: String,
    val priority: Priority,
    val deadline: LocalDate? = null,
    val isDone: Boolean,
    val creationDate: LocalDate,
    val editDate: LocalDate? = null,
)

enum class Priority {
    HIGH, REGULAR, LOW
}