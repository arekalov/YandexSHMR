package com.arekalov.yandexshmr.data.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Revision dto class
 */
@Entity(tableName = "revision_table")
data class RevisionDbDto(
    @PrimaryKey val id: String = "0",
    @ColumnInfo(name = "revision") val revisionNumber: Int = 0
)