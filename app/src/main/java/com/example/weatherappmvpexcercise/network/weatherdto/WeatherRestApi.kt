package com.example.weatherappmvpexcercise.network.weatherdto

import com.example.weatherappmvpexcercise.Utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherRestApi {

    private val okHttpClient = buildOkHttpClient()
    private val retrofit = buildRetrofit()

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(WeatherApiKeyInterceptor.create())
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()
    }

    fun getEndPoint(): WeatherEndPoint {
        return retrofit.create(WeatherEndPoint::class.java)
    }

    companion object {

        private const val TIMEOUT_IN_SECONDS = 5
    }

}
