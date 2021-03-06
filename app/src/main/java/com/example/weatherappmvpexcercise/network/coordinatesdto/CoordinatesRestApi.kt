package com.example.weatherappmvpexcercise.network.coordinatesdto

import com.example.weatherappmvpexcercise.Utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CoordinatesRestApi {

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(CoordinatesApiKeyInterceptor.create())
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()
    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(Constants.COORDINATES_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getEndPoint(): CoordinatesEndPoint {
        return retrofit.create(CoordinatesEndPoint::class.java)
    }

    companion object {

        private const val TIMEOUT_IN_SECONDS = 5
    }
}
