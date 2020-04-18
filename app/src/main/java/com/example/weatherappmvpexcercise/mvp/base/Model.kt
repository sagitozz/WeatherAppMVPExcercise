package com.example.weatherappmvpexcercise.mvp.base


import android.location.Location
import android.util.Log

import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.network.dto.RestApi
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import retrofit2.Call

class Model {

    private val restApi: RestApi? = RestApi()

    fun modelGetWeather(latitude: Double, longitude: Double): Call<WeatherResponse?>? {
        Log.d(Constants.LOG_TAG, "Запрос из модели")
        return restApi?.getEndPoint()?.getWeather(latitude, longitude)
    }
}
