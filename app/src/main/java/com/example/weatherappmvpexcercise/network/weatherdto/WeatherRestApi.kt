package com.example.weatherappmvpexcercise.network.weatherdto

import com.example.weatherappmvpexcercise.constants.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherRestApi {

    private var sRestApi: WeatherRestApi? = null
    private var sEndPoint: WeatherEndPoint? = null

    init {
        val okHttpClient = buildOkHttpClient()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        sEndPoint = retrofit.create(WeatherEndPoint::class.java)
    }

    @Synchronized
    fun getInstance(): WeatherRestApi? {
        if (sRestApi == null) {
            sRestApi = WeatherRestApi()
        }
        return sRestApi
    }

    fun getEndPoint(): WeatherEndPoint? {
        return sEndPoint
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(WeatherApiKeyInterceptor.create())
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()
    }

    companion object {

        private const val TIMEOUT_IN_SECONDS = 5
    }

}
