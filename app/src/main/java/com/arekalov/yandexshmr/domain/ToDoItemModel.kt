package com.arekalov.yandexshmr.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class ToDoItem(
    val id: String,
    val task: String,
    val priority: Priority,
    val deadline: LocalDate? = null,
    val isDone: Boolean,
    val creationDate: LocalDate,
    val editDate: LocalDate? = null,
) : Parcelable

enum class Priority {
    HIGH, REGULAR, LOW
}