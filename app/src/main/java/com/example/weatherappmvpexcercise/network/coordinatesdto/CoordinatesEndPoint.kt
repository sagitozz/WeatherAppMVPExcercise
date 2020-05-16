package com.example.weatherappmvpexcercise.network.coordinatesdto

import retrofit2.Call
import retrofit2.http.GET

interface CoordinatesEndPoint {

    @GET("https://api.sypexgeo.net/")
    fun getCoordinates(): Call<CoordinatesResponse>
}