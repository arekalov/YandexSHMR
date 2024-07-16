package com.arekalov.yandexshmr.data

import com.arekalov.yandexshmr.data.network.RetrofitClient
import com.arekalov.yandexshmr.data.network.RetrofitConfig
import com.arekalov.yandexshmr.data.network.ToDoItemApi
import com.arekalov.yandexshmr.data.network.ToDoItemsNetworkDataSource
import com.arekalov.yandexshmr.data.network.interceptors.AuthInterceptor
import com.arekalov.yandexshmr.data.network.interceptors.RetryInterceptor
import com.arekalov.yandexshmr.presentation.MainActivity
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor

fun main() {
    val config = RetrofitConfig(
        baseUrl = "https://hive.mrdekk.ru/todo/",
        interceptors = listOf(
            AuthInterceptor("OAuth y0_AgAAAABFoKeYAARC0QAAAAEJaRqeAACIG7tOI8RD04z_dB2BIIrJ0pw7FQ"),
            RetryInterceptor(),
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
    )
    val api = RetrofitClient.getInstance(config).create(ToDoItemApi::class.java)
    val ds = ToDoItemsNetworkDataSource(api)
    runBlocking {
        println(ds.deleteItem("d2d643c4-adaa-4861-b25a-45d854b81d28"))
    }
}