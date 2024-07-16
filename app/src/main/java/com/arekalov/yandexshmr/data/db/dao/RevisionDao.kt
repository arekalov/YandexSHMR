package com.arekalov.yandexshmr.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.arekalov.yandexshmr.data.db.dto.RevisionDbDto

@Dao
interface RevisionDao {
    @Query("SELECT * FROM revision_table WHERE id = 0")
    suspend fun getRevision(): RevisionDbDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateRevision(revision: RevisionDbDto)

    @Transaction
    suspend fun incrementRevision() {
        val currentRevision = getRevision()
        val newRevision = currentRevision.copy(revisionNumber = currentRevision.revisionNumber + 1)
        insertOrUpdateRevision(newRevision)
    }

    @Transaction
    suspend fun updateRevisionTo(newRevisionNumber: Int) {
        val currentRevision = getRevision()
        val newRevision = currentRevision.copy(revisionNumber = newRevisionNumber)
        insertOrUpdateRevision(newRevision)
    }
}
