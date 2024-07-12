package com.arekalov.yandexshmr.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arekalov.yandexshmr.data.db.dto.ToDoItemElementDbDto

/**
 *DAO for Room db
 **/

@Dao
interface ToDoItemsDao {
    @Query("""SELECT * FROM items""")
    suspend fun getToDoItems(): List<ToDoItemElementDbDto>

    @Query("""SELECT * FROM items WHERE id = :id""")
    suspend fun getToDoItem(id: String): ToDoItemElementDbDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDoItem(item: ToDoItemElementDbDto)

    @Update
    suspend fun updateToDoItem(item: ToDoItemElementDbDto)

    @Query("""DELETE FROM items WHERE id = :id""")
    suspend fun deleteToDoItem(id: String)

    @Query("""DELETE FROM items""")
    suspend fun deleteAllToDoItems()
}