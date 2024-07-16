package com.arekalov.yandexshmr.data.db.mappers

import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import java.time.LocalDate

fun ToDoItemElementDbDto.toToDoItemModel(): ToDoItemModel {
    return ToDoItemModel(
        id = this.id,
        task = this.task ?: "",
        priority = this.priority,
        deadline = if (this.deadline == null) null else LocalDate.parse(this.deadline),
        isDone = this.isDone,
        creationDate = LocalDate.parse(this.creationDate),
    editDate = if (this.editDate == null) null else LocalDate.parse(this.editDate)
    )
}
