package com.example.midterm_labact.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!NetworkUtils.isOnline(context)) {
            val maxStale = TimeUnit.DAYS.toSeconds(7).toInt()
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }

        return chain.proceed(request)
    }
}
