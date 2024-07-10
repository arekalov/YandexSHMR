package com.arekalov.yandexshmr.data.mappers

import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.data.dto.ToDoItemElementDto
import com.arekalov.yandexshmr.data.dto.ToDoItemListDto
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import java.time.LocalDate

/**
Extensions for mapping DTO to Models from domain
 **/

fun ToDoItemListDto.toToDoItemListModel(): List<ToDoItemModel> {
    return this.list.map { it.toToDoItemModel() }
}

fun ToDoItemDto.toToDoItemModel(): ToDoItemModel {
    return ToDoItemModel(
        id = this.id,
        task = this.text,
        priority = this.importance.toPriority(),
        deadline = this.deadline?.let { LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000)) },
        isDone = this.done,
        creationDate = LocalDate.ofEpochDay(this.created_at / (24 * 60 * 60 * 1000)),
        editDate = this.changed_at.takeIf { it != this.created_at }
            ?.let { LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000)) }
    )
}

fun String.toPriority(): Priority {
    return when (this.lowercase()) {
        "important" -> Priority.HIGH
        "low" -> Priority.LOW
        else -> Priority.REGULAR
    }
}

fun ToDoItemElementDto.toToDoItemModel(): ToDoItemModel {
    return this.element.toToDoItemModel()
}