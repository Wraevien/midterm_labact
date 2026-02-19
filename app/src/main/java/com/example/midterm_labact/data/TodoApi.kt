package com.example.midterm_labact.data

import retrofit2.http.GET
import retrofit2.http.Query

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): List<TodoDto>
}
