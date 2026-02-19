package com.example.midterm_labact.network

import okhttp3.Interceptor
import okhttp3.Response

class OnlineInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder()
            .header("Cache-Control", "public, max-age=60")
            .removeHeader("Pragma")
            .build()
    }
}
