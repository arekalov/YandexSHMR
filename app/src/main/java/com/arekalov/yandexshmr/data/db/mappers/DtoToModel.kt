package com.arekalov.yandexshmr.data.db.mappers

import android.util.Log
import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import java.time.LocalDate

fun ToDoItemElementDbDto.toToDoItemModel(): ToDoItemModel {
//    Log.e("!!!", "deadlein: ${this.deadline}", )
//    Log.e("!!!", "creation: ${this.creationDate}", )
//    Log.e("!!!", "edit: ${this.editDate}", )
//    Log.e("!!!", "this: ${this}", )
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
