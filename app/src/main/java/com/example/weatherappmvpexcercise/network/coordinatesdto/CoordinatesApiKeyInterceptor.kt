package com.example.weatherappmvpexcercise.network.coordinatesdto

import com.example.weatherappmvpexcercise.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class CoordinatesApiKeyInterceptor private constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithApiKey = chain.request()
        val url = requestWithApiKey.url
            .newBuilder()
            .addQueryParameter("access_key", Constants.COORDINATES_API_KEY)
            .addQueryParameter("language", "ru")
            .build()

        val requestWithAttachedApiKey = requestWithApiKey.newBuilder()
            .url(url)
            .build()
        return chain.proceed(requestWithAttachedApiKey)
    }

    companion object {

        private val sInstance: CoordinatesApiKeyInterceptor by lazy { CoordinatesApiKeyInterceptor() }

        fun create(): Interceptor {
            return sInstance
        }
    }
}
