package com.example.weatherappmvpexcercise.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("data") val data: MutableList<DataItem>,
    @SerializedName("count") val count: Int = 0,
    @SerializedName("city_name") val city_name: String = ""
)
