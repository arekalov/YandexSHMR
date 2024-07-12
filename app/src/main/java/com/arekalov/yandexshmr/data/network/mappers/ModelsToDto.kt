package com.arekalov.yandexshmr.data.network.mappers

import com.arekalov.yandexshmr.data.network.dto.ToDoItemNetworkDto
import com.arekalov.yandexshmr.data.network.dto.ToDoItemElementToSendNetworkDto
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import java.time.LocalDate
import java.time.ZoneOffset


/**
Extensions for mapping Models to DTO from domain
 **/

fun ToDoItemModel.toToDoItemElementToSend(): ToDoItemElementToSendNetworkDto {
    return ToDoItemElementToSendNetworkDto(
        element = this.toToDoItemDto()
    )
}

fun ToDoItemModel.toToDoItemDto(): ToDoItemNetworkDto {
    return ToDoItemNetworkDto(
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