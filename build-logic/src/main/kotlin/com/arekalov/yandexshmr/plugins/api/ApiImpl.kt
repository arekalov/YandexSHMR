package com.arekalov.yandexshmr.plugins.api

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File

private const val BASE_URL = "https://api.telegram.org/"

class TelegramApi(
    private val apiService: TelegramApiService
) {
    suspend fun upload(file: File, token: String, chatId: String): ResponseBody {
        val requestFile = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("document", file.name, requestFile)

        return withContext(Dispatchers.IO) {
            apiService.upload(token, chatId, body)
        }
    }

    suspend fun sendMessage(message: String, token: String, chatId: String) {
        return withContext(Dispatchers.IO) {
            apiService.sendMessage(token, chatId, message)
        }
    }

    companion object {
        fun create(): TelegramApi {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(TelegramApiService::class.java)
            return TelegramApi(apiService)
        }
    }
}
