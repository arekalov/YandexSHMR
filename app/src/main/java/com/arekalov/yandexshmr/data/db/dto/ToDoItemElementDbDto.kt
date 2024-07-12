package com.arekalov.yandexshmr.data.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arekalov.yandexshmr.domain.model.Priority
import java.time.LocalDate

/**
 *DTO for saving to Room
 **/

@Entity(tableName = "items")
data class ToDoItemElementDbDto(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "item_type") val itemType: String, // wil be add in next HW
    @ColumnInfo(name = "task") val task: String?,
    @ColumnInfo(name = "voice_path") val voicePath: String?, // wil be add in next HW
    @ColumnInfo(name = "priority") val priority: Priority,
    @ColumnInfo(name = "deadline") val deadline: String?,
    @ColumnInfo(name = "isDone") val isDone: Boolean,
    @ColumnInfo(name = "creationName") val creationDate: String,
    @ColumnInfo(name = "editDate") val editDate: String?,
)