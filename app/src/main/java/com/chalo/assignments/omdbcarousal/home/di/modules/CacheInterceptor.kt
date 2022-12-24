package com.chalo.assignments.omdbcarousal.home.di.modules

import com.chalo.assignments.omdbcarousal.home.repository.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CacheInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val cacheControl = CacheControl
            .Builder()
            .maxAge(NetworkUtils.CACHE_PERIOD, TimeUnit.MINUTES)
            .build()

        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}