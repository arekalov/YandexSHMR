package com.arekalov.yandexshmr.domain.di

import com.arekalov.yandexshmr.data.repository.ToDoItemRepositoryImpl
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Singleton
    @Binds
    fun bindsRepository(toDoItemRepositoryImpl: ToDoItemRepositoryImpl): ToDoItemRepository
}