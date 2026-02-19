package com.example.midterm_labact.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val api: TodoApi) {
    fun getTodosPaged(pageSize: Int = 20): Flow<PagingData<TodoDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TodoPagingSource(api, pageSize) }
        ).flow
    }
}
