package com.example.weatherappmvpexcercise.network.dto

import com.example.weatherappmvpexcercise.Constants.Constants
import com.example.weatherappmvpexcercise.network.WeatherEndPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApi {

    private val TIMEOUT_IN_SECONDS = 2
    private var sRestApi: RestApi? = null
    private var sEndPoint: WeatherEndPoint? = null

    init {

        val okHttpClient = buildOkHttpClient()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("weatherbit-v1-mashape.p.rapidapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        sEndPoint = retrofit.create(WeatherEndPoint::class.java)
    }

    @Synchronized
    fun getInstance(): RestApi? {
        if (sRestApi == null) {
            sRestApi = RestApi()
        }
        return sRestApi
    }

    fun getEndPoint(): WeatherEndPoint? {
        return sEndPoint
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()
    }


}