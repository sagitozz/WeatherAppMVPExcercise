package com.example.weatherappmvpexcercise.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    //todo замени MutableList на List если ты не планируешь его изменять!!!
    // В твоем случае ты только получаешь данные из него
    @SerializedName("data") val data: MutableList<DataItem>,
    @SerializedName("count") val count: Int = 0,
    @SerializedName("city_name") val city_name: String = ""
)
