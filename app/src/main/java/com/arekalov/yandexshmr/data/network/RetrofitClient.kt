package com.arekalov.yandexshmr.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
Retrofit client, that implement api repository
 **/

data class RetrofitConfig(
    val baseUrl: String,
    val interceptors: List<Interceptor> = emptyList()
)

object RetrofitClient {
    fun getInstance(config: RetrofitConfig): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            config.interceptors.forEach { addInterceptor(it) }
        }.build()

        return Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}