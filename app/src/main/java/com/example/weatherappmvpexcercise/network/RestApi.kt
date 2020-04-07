package com.example.weatherappmvpexcercise.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



class RestApi {

    private val TIMEOUT_IN_SECONDS = 2
    private var sRestApi: RestApi? = null
    private var sEndPoint: WeatherEndPoint? = null

    private fun RestApi() {
        val okHttpClient: OkHttpClient = buildOkHttpClient()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        sEndPoint = retrofit.create(WeatherEndPoint)
    }

    @get:Synchronized
    val instance: RestApi?
        get() {
            if (sRestApi == null) {
                sRestApi = RestApi()
            }
            return sRestApi
        }

    val endPoint: NewsEndPoint?
        get() = sEndPoint

    private fun buildOkHttpClient(): OkHttpClient {
        return Builder()
            .addInterceptor(ApiKeyInterceptor.create())
            .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()
    }