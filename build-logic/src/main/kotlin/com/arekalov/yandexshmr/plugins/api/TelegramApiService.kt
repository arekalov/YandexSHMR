package com.arekalov.yandexshmr.plugins.api


import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface TelegramApiService {
    @Multipart
    @POST("/bot{token}/sendDocument")
    suspend fun upload(
        @Path("token") token: String,
        @Query("chat_id") chatId: String,
        @Part document: MultipartBody.Part
    ): ResponseBody

    @GET("/bot{token}/sendMessage")
    suspend fun sendMessage(
        @Path("token") token: String?,
        @Query("chat_id") chatId: String?,
        @Query("text") text: String?
    ): ResponseBody
}
