package com.example.weatherappmvpexcercise.network.coordinatesdto

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class CoordinatesApiKeyInterceptor private constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithApiKey = chain.request()
        val url = requestWithApiKey.url
            .newBuilder()
            .build()

        val requestWithAttachedApiKey = requestWithApiKey.newBuilder()
            .url(url)
            .build()
        return chain.proceed(requestWithAttachedApiKey)
    }

    companion object {
        private val sInstance: CoordinatesApiKeyInterceptor by lazy { CoordinatesApiKeyInterceptor() }

        fun create(): Interceptor = sInstance
    }
}
