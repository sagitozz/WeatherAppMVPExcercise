package com.example.weatherappmvpexcercise.network.dto

import com.example.weatherappmvpexcercise.Constants.Constants
import com.example.weatherappmvpexcercise.network.WeatherEndPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi {

    private val TIMEOUT_IN_SECONDS = 2
    private val sRestApi: RestApi? = null
    private var sEndPoint: WeatherEndPoint? = null

    private fun RestApi() {
        val okHttpClient: OkHttpClient = buildOkHttpClient()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        sEndPoint = retrofit.create<WeatherResponse>(WeatherEndPoint::class.java)
    }
}