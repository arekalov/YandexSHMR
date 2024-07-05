package com.arekalov.yandexshmr.data.mappers

import com.arekalov.yandexshmr.data.dto.ToDoItemDto
import com.arekalov.yandexshmr.data.dto.ToDoItemElementToSendDto
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import java.time.LocalDate
import java.time.ZoneOffset


/**
Extensions for mapping Models to DTO from domain
 **/

fun ToDoItemModel.toToDoItemElementToSend(): ToDoItemElementToSendDto {
    return ToDoItemElementToSendDto(
        element = this.toToDoItemDto()
    )
}

fun ToDoItemModel.toToDoItemDto(): ToDoItemDto {
    return ToDoItemDto(
        id = this.id,
        text = this.task,
        importance = this.priority.toImportanceString(),
        deadline = this.deadline?.toEpochMilli(),
        done = this.isDone,
        color = null,
        created_at = this.creationDate.toEpochMilli(),
        changed_at = this.editDate?.toEpochMilli() ?: this.creationDate.toEpochMilli(),
        last_updated_by = "0"
    )
}

fun Priority.toImportanceString(): String {
    return when (this) {
        Priority.HIGH -> "important"
        Priority.LOW -> "low"
        Priority.REGULAR -> "basic"
    }
}

fun LocalDate.toEpochMilli(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}