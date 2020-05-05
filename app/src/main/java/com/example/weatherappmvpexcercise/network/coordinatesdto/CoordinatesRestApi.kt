package com.example.weatherappmvpexcercise.network.coordinatesdto

import com.example.weatherappmvpexcercise.constants.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CoordinatesRestApi {

    private var sRestApi: CoordinatesRestApi? = null
    private var sEndPoint: CoordinatesEndPoint? = null

    init {
        val okHttpClient = buildOkHttpClient()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.COORDINATES_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        sEndPoint = retrofit.create(CoordinatesEndPoint::class.java)
    }

    @Synchronized
    fun getInstance(): CoordinatesRestApi? {
        if (sRestApi == null) {
            sRestApi = CoordinatesRestApi()
        }
        return sRestApi
    }

    fun getEndPoint(): CoordinatesEndPoint? {
        return sEndPoint
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()
    }

    companion object {

        private const val TIMEOUT_IN_SECONDS = 5
    }

}
