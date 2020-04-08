package com.example.weatherappmvpexcercise.mvp.base

import com.example.weatherappmvpexcercise.network.RestApi
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import retrofit2.Call

class Model {

    private val restApi: RestApi? = RestApi.instance

    fun modelGetWeather(): Call<WeatherResponse?>? {
        return restApi.g
    }
}