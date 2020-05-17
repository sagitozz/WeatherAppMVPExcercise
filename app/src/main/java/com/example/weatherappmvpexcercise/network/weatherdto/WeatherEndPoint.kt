package com.example.weatherappmvpexcercise.network.weatherdto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherEndPoint {

    @GET("/forecast/3hourly")
    fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String
    ): Call<WeatherResponse>


}
