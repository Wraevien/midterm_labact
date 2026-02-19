package com.example.midterm_labact.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

class TodoPagingSource(
    private val api: TodoApi,
    private val pageSize: Int
) : PagingSource<Int, TodoDto>() {

    override fun getRefreshKey(state: PagingState<Int, TodoDto>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TodoDto> {
        val page = params.key ?: 1

        return try {
            val data = api.getTodos(page = page, limit = pageSize)

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
