package com.example.midterm_labact.di

import android.content.Context
import com.example.midterm_labact.data.TodoApi
import com.example.midterm_labact.network.OfflineInterceptor
import com.example.midterm_labact.network.OnlineInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val CACHE_SIZE_BYTES: Long = 10L * 1024L * 1024L // 10MB

    fun provideTodoApi(context: Context): TodoApi {
        val cacheDir = File(context.cacheDir, "http_cache")
        val cache = Cache(cacheDir, CACHE_SIZE_BYTES)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(OfflineInterceptor(context))     // offline: cache-first
            .addNetworkInterceptor(OnlineInterceptor())      // online: inject cache header
            .addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TodoApi::class.java)
    }
}
