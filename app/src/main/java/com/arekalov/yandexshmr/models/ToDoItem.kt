package com.arekalov.yandexshmr.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate

data class ToDoItem(
    var id: MutableState<String>,
    var task: MutableState<String>,
    var priority: MutableState<Priority>,
    var deadline: MutableState<LocalDate?> = mutableStateOf(null),
    var isDone: MutableState<Boolean>,
    var creationDate: MutableState<LocalDate>,
    var editDate: MutableState<LocalDate?> = mutableStateOf(null),
)

enum class Priority {
    HIGH, REGULAR, LOW
}