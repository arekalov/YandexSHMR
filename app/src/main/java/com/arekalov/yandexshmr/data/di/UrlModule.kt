package com.arekalov.yandexshmr.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UrlModule {
    @Provides
    @Singleton
    fun provideUrl(): String = "https://hive.mrdekk.ru/todo/"
}