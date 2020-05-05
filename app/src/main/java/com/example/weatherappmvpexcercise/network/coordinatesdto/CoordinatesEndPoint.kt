package com.example.weatherappmvpexcercise.network.coordinatesdto

import CoordinatesResponse
import retrofit2.Call
import retrofit2.http.GET

interface CoordinatesEndPoint {

    @GET("check?")
    fun getCoordinates(
    ): Call<CoordinatesResponse?>?

}