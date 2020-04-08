package com.example.weatherappmvpexcercise.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

	@field:SerializedName("data")

	val data: List<DataItem?>? = null,


	@field:SerializedName("count")
	val count: Int? = null


)