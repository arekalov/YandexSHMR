package com.arekalov.yandexshmr.data.di

import android.content.Context
import androidx.room.Room
import com.arekalov.yandexshmr.data.db.ToDoItemsDB
import com.arekalov.yandexshmr.data.db.dao.RevisionDao
import com.arekalov.yandexshmr.data.db.dao.ToDoItemsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ToDoItemsDB {
        return Room.databaseBuilder(
            appContext,
            ToDoItemsDB::class.java,
            "items_database"
        ).build()
    }

    @Provides
    fun provideToDoItemsDao(db: ToDoItemsDB): ToDoItemsDao {
        return db.userDao()
    }

    @Provides
    fun provideRevisionDao(db: ToDoItemsDB): RevisionDao {
        return db.revisionDao()
    }

}