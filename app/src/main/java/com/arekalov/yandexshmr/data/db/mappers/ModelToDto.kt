package com.arekalov.yandexshmr.data.db.mappers

import com.arekalov.yandexshmr.data.db.dto.ItemType
import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto
import com.arekalov.yandexshmr.domain.model.ToDoItemModel

fun ToDoItemModel.toToDoItemElementDbDto(): ToDoItemElementDbDto {
    return ToDoItemElementDbDto(
        id = this.id,
        itemType = ItemType.TEXT, // will be changed in next HW
        task = this.task,
        voicePath = null, // will be added in next HW
        priority = this.priority,
        deadline = this.deadline,
        isDone = this.isDone,
        creationDate = this.creationDate,
        editDate = this.editDate
    )
}