package com.example.weatherappmvpexcercise.mvp.base

import android.util.Log
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesResponse
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesRestApi
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherResponse
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherRestApi
import retrofit2.Call

class Model {

    private val weatherRestApi: WeatherRestApi? = WeatherRestApi()
    private val coordinatesRestApi: CoordinatesRestApi = CoordinatesRestApi()

    fun modelGetWeather(
        latitude: Double,
        longitude: Double,
        language: String
    ): Call<WeatherResponse> {
        Log.d(Constants.LOG_TAG, "Запрос из модели")
        return weatherRestApi?.getEndPoint()!!.getWeather(latitude, longitude, language)
    }

    fun modelGetCoordinatesByIp(): Call<CoordinatesResponse> {
        return coordinatesRestApi.getEndPoint()!!.getCoordinates()
    }
}
