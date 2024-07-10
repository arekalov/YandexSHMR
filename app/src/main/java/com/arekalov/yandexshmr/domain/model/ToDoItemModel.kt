package com.arekalov.yandexshmr.domain.model

import java.time.LocalDate

/**
Clean ToDoItem, that use state.
 **/

data class ToDoItemModel(
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