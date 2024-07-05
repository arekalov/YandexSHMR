package com.arekalov.yandexshmr.data.network

import com.arekalov.yandexshmr.data.network.interceptors.AuthInterceptor
import com.arekalov.yandexshmr.data.network.interceptors.RetryInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://hive.mrdekk.ru/todo/"
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
//        .addInterceptor(ErrorGeneratorInterceptor())
        .addInterceptor(RetryInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}