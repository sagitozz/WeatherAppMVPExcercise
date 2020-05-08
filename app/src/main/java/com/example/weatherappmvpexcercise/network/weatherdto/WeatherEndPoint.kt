package com.example.weatherappmvpexcercise.network.weatherdto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherEndPoint {

    //    @Headers(
//        "x-rapidapi-key: 1d0a2e860bmsh0b9d1011b14e5a3p1139aejsn3cb8586f5524",
//        "x-rapidapi-host: weatherbit-v1-mashape.p.rapidapi.com"
//    )
    @GET("/forecast/3hourly")
    fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String
    ): Call<WeatherResponse>


}
