package com.arekalov.yandexshmr.data.di

import android.content.Context
import com.arekalov.yandexshmr.BuildConfig
import com.arekalov.yandexshmr.data.network.NetworkConnectionManager
import com.arekalov.yandexshmr.data.network.RetrofitConfig
import com.arekalov.yandexshmr.data.network.ToDoItemApi
import com.arekalov.yandexshmr.data.network.interceptors.AuthInterceptor
import com.arekalov.yandexshmr.data.network.interceptors.RetryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofitConfig(
        url: String
    ): RetrofitConfig {
        val interceptors = listOf<Interceptor>(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
            RetryInterceptor(),
            AuthInterceptor(token = BuildConfig.OAUTH_AUTHORIZATION)
        )
        return RetrofitConfig(
            baseUrl = url,
            interceptors = interceptors
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(config: RetrofitConfig): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            config.interceptors.forEach { addInterceptor(it) }
        }.build()

        return Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideToDoItemApi(retrofit: Retrofit): ToDoItemApi {
        return retrofit.create(ToDoItemApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionManager(
        @ApplicationContext context: Context
    ): NetworkConnectionManager {
        return NetworkConnectionManager(context)
    }
}