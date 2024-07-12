package com.arekalov.yandexshmr.data.db.mappers

import android.util.Log
import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto
import com.arekalov.yandexshmr.domain.model.ToDoItemModel

fun ToDoItemModel.toToDoItemElementDbDto(): ToDoItemElementDbDto {

//    Log.e("!!!", "Zdeadlein: ${this.deadline}", )
//    Log.e("!!!", "Zcreation: ${this.creationDate}", )
//    Log.e("!!!", "Zedit: ${this.editDate}", )
    return ToDoItemElementDbDto(
        id = this.id,
        itemType = "", // will be changed in next HW
        task = this.task,
        voicePath = null, // will be added in next HW
        priority = this.priority,
        deadline = if (this.deadline == null) null else this.deadline.toString(),
        isDone = this.isDone,
        creationDate = this.creationDate.toString(),
        editDate = if (this.editDate == null) null else this.editDate.toString(),
    )
}