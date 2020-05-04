package com.example.weatherappmvpexcercise.mvp.base

import android.util.Log
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherResponse
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherRestApi
import retrofit2.Call

class Model {

    private val restApi: WeatherRestApi? = WeatherRestApi()

    fun modelGetWeather(latitude: Double, longitude: Double, language: String): Call<WeatherResponse?>? {
        Log.d(Constants.LOG_TAG, "Запрос из модели")
        return restApi?.getEndPoint()?.getWeather(latitude, longitude, language)
    }
}
