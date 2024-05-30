package com.aplimelta.todos.data.retrofit

import com.aplimelta.todos.data.response.TodosResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): Response<List<TodosResponseItem>>

    @GET("todos/{id}")
    suspend fun getTodoItem(
        @Path("id") id: String
    ): Response<TodosResponseItem>
}