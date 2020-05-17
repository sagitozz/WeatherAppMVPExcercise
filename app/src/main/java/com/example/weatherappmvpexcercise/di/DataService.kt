package com.example.weatherappmvpexcercise.di

import com.example.weatherappmvpexcercise.Utils.Logger.log
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesResponse
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesRestApi
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherResponse
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherRestApi
import retrofit2.Call

interface DataService {

    fun getWeather(
        latitude: Double,
        longitude: Double,
        language: String
    ): Call<WeatherResponse>

    fun modelGetCoordinatesByIp(): Call<CoordinatesResponse>

}

class DataServiceImpl(
    private val weatherRestApi: WeatherRestApi,
    private val coordinatesRestApi: CoordinatesRestApi
) : DataService {

    override fun getWeather(
        latitude: Double,
        longitude: Double,
        language: String
    ): Call<WeatherResponse> {
        log("Запрос из модели")
        return weatherRestApi.getEndPoint().getWeather(latitude, longitude, language)
    }

    override fun modelGetCoordinatesByIp(): Call<CoordinatesResponse> {
        return coordinatesRestApi.getEndPoint().getCoordinates()
    }

}
