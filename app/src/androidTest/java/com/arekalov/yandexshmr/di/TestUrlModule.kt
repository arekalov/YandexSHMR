package com.arekalov.yandexshmr.di

import com.arekalov.yandexshmr.data.di.UrlModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UrlModule::class]
)
object TestUrlModule {
    @Provides
    @Singleton
    fun provideUrl(): String = "http://localhost" // for run on real device change to localhost
}