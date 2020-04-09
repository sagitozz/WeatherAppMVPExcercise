package com.example.weatherappmvpexcercise.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
	@SerializedName("data") val data: List<DataItem> = emptyList(),
	@SerializedName("count") val count: Int = 0
)
