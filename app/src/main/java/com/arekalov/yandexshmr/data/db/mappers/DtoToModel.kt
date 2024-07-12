package com.arekalov.yandexshmr.data.db.mappers

import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto
import com.arekalov.yandexshmr.domain.model.ToDoItemModel

fun ToDoItemElementDbDto.toToDoItemModel(): ToDoItemModel {
    return ToDoItemModel(
        id = this.id,
        task = this.task ?: "",
        priority = this.priority,
        deadline = this.deadline,
        isDone = this.isDone,
        creationDate = this.creationDate,
        editDate = this.editDate
    )
}
