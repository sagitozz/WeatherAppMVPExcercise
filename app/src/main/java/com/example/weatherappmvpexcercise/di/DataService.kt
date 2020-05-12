package com.example.weatherappmvpexcercise.di

import android.util.Log
import com.example.weatherappmvpexcercise.Utils.Constants
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesResponse
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesRestApi
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherResponse
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherRestApi
import retrofit2.Call

interface DataService {
    fun getWeather( latitude: Double,
                    longitude: Double,
                    language: String): Call<WeatherResponse>

    fun modelGetCoordinatesByIp() : Call<CoordinatesResponse>

}

class DataServiceImpl : DataService {
    private val weatherRestApi: WeatherRestApi = WeatherRestApi()
    private val coordinatesRestApi: CoordinatesRestApi = CoordinatesRestApi()
    override fun getWeather(latitude: Double, longitude: Double, language: String): Call<WeatherResponse> {
        Log.d(Constants.LOG_TAG, "Запрос из модели")
        return weatherRestApi.getEndPoint()!!.getWeather(latitude, longitude, language)
    }

    override fun modelGetCoordinatesByIp(): Call<CoordinatesResponse> {
        return coordinatesRestApi.getEndPoint()!!.getCoordinates()
    }

}