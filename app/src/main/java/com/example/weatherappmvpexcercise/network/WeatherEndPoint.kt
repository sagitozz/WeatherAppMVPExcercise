package com.example.weatherappmvpexcercise.network

import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherEndPoint {

    @GET("home.json")
    fun getWeather(): Call<WeatherResponse?>?



//    @GET("{section}.json")
//    fun getNewsByCategory(@Path("section") category: String?): Call<NewsResponse?>?
}