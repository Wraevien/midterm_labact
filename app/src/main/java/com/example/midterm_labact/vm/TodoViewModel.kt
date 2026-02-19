package com.example.midterm_labact.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.midterm_labact.data.TodoDto
import com.example.midterm_labact.data.TodoRepository
import com.example.midterm_labact.di.NetworkModule
import kotlinx.coroutines.flow.Flow

class TodoViewModel(app: Application) : AndroidViewModel(app) {

    private val api = NetworkModule.provideTodoApi(app.applicationContext)
    private val repo = TodoRepository(api)

    val todos: Flow<PagingData<TodoDto>> =
        repo.getTodosPaged(pageSize = 20).cachedIn(viewModelScope)
}
