package com.arekalov.yandexshmr.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arekalov.yandexshmr.data.db.dao.RevisionDao
import com.arekalov.yandexshmr.data.db.dao.ToDoItemsDao
import com.arekalov.yandexshmr.data.db.dto.RevisionDbDto
import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto

/**
 * DB implementation class
 */

private const val dbName = "items_database"

@Database(
    entities = [ToDoItemElementDbDto::class, RevisionDbDto::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoItemsDB : RoomDatabase() {
    abstract fun userDao(): ToDoItemsDao
    abstract fun revisionDao(): RevisionDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoItemsDB? = null

        fun getDatabase(context: Context): ToDoItemsDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoItemsDB::class.java,
                    dbName
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}