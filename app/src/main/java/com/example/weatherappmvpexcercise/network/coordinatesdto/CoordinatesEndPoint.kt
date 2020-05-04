package com.example.weatherappmvpexcercise.network.coordinatesdto

import CoordinatesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoordinatesEndPoint {

    @GET("check?")
    fun getCoordinates(
        @Query("access_key") accessKey: String,
        @Query("language") language: String
    ): Call<CoordinatesResponse?>?

}