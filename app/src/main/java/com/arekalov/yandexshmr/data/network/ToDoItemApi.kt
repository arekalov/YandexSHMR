package com.arekalov.yandexshmr.data.network

import com.arekalov.yandexshmr.data.dto.ToDoItemElementDto
import com.arekalov.yandexshmr.data.dto.ToDoItemElementToSendDto
import com.arekalov.yandexshmr.data.dto.ToDoItemListDto
import com.arekalov.yandexshmr.data.dto.ToDoItemListToSendDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
Methods for send and get data from server
 **/

interface ToDoItemApi {

    @GET("list")
    suspend fun getToDoList(): Response<ToDoItemListDto>

    @PATCH("list")
    suspend fun updateToDoList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body toDoList: ToDoItemListToSendDto
    ): Response<ToDoItemListDto>

    @GET("list/{id}")
    suspend fun getToDoItem(
        @Path("id") id: String
    ): Response<ToDoItemElementDto>

    @POST("list")
    suspend fun addToDoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body toDoItem: ToDoItemElementToSendDto
    ): Response<ToDoItemElementDto>

    @PUT("list/{id}")
    suspend fun updateToDoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body toDoItem: ToDoItemElementToSendDto
    ): Response<ToDoItemElementDto>

    @DELETE("list/{id}")
    suspend fun deleteToDoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): Response<ToDoItemElementDto>
}