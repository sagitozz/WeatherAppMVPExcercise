package com.example.weatherappmvpexcercise.network.weatherdto

import com.example.weatherappmvpexcercise.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class WeatherApiKeyInterceptor private constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithoutApiKey = chain.request()
        val url = requestWithoutApiKey.url
            .newBuilder()
            .build()

        val requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
            .url(url)
            .addHeader("x-rapidapi-host", "weatherbit-v1-mashape.p.rapidapi.com")
            .addHeader(API_KEY_KEY, Constants.WEATHER_API_KEY)
            .build()
        return chain.proceed(requestWithAttachedApiKey)
    }

    companion object {

        private const val API_KEY_KEY = "x-rapidapi-key"
        private val sInstance: WeatherApiKeyInterceptor by lazy { WeatherApiKeyInterceptor() }

        fun create(): Interceptor {
            return sInstance
        }
    }
}
