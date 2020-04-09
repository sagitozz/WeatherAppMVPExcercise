package com.example.weatherappmvpexcercise.mvp.base


import com.example.weatherappmvpexcercise.network.dto.RestApi
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import retrofit2.Call

class Model {

    private val restApi: RestApi? = RestApi()

    fun modelGetWeather(): Call<WeatherResponse?>? {
        return restApi?.getEndPoint()?.getWeather(33.0, 33.0)
    }
}
