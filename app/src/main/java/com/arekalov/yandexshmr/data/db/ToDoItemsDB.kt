package com.arekalov.yandexshmr.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto

/**
 * DB implementation class
 */

@Database(entities = [ToDoItemElementDbDto::class], version = 1)
abstract class ToDoItemsDB : RoomDatabase() {
    abstract fun userDao(): ToDoItemsDao
    companion object {
        @Volatile
        private var INSTANCE: ToDoItemsDB? = null

        fun getDatabase(context: Context): ToDoItemsDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoItemsDB::class.java,
                    "items_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}