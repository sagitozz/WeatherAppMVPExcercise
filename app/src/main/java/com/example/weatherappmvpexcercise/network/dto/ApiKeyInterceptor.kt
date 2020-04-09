package com.example.weatherappmvpexcercise.network.dto

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class ApiKeyInterceptor private constructor(private val apiKey: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithoutApiKey = chain.request()
        val url = requestWithoutApiKey.url
            .newBuilder()
            .addQueryParameter(API_KEY, apiKey)
            .build()
        val requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
            .url(url)
            .header("x-rapidapi-host", "weatherbit-v1-mashape.p.rapidapi.com")
            .header("x-rapidapi-key", "1d0a2e860bmsh0b9d1011b14e5a3p1139aejsn3cb8586f5524")
            .build()
        return chain.proceed(requestWithAttachedApiKey)
    }

    companion object {
        private const val API_KEY = "api-key"
        private var sInstance: ApiKeyInterceptor? = null
        fun create(): Interceptor? {
            if (sInstance == null) {
                sInstance = ApiKeyInterceptor("1d0a2e860bmsh0b9d1011b14e5a3p1139aejsn3cb8586f5524")
            }
            return sInstance
        }
    }

}