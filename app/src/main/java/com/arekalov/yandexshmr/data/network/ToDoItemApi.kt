package com.arekalov.yandexshmr.data.network

import com.arekalov.yandexshmr.data.network.dto.ToDoItemElementNetworkDto
import com.arekalov.yandexshmr.data.network.dto.ToDoItemElementToSendNetworkDto
import com.arekalov.yandexshmr.data.network.dto.ToDoItemListNetworkDto
import com.arekalov.yandexshmr.data.network.dto.ToDoItemListToSendNetworkDto
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
    suspend fun getToDoItems(): Response<ToDoItemListNetworkDto>

    @PATCH("list")
    suspend fun updateToDoList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body toDoList: ToDoItemListToSendNetworkDto
    ): Response<ToDoItemListNetworkDto>

    @GET("list/{id}")
    suspend fun getToDoItem(
        @Path("id") id: String
    ): Response<ToDoItemElementNetworkDto>

    @POST("list")
    suspend fun addToDoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body toDoItem: ToDoItemElementToSendNetworkDto
    ): Response<ToDoItemElementNetworkDto>

    @PUT("list/{id}")
    suspend fun updateToDoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body toDoItem: ToDoItemElementToSendNetworkDto
    ): Response<ToDoItemElementNetworkDto>

    @DELETE("list/{id}")
    suspend fun deleteToDoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): Response<ToDoItemElementNetworkDto>
}