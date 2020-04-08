package com.example.weatherappmvpexcercise.network.dto

import com.google.gson.annotations.SerializedName

data class Weather(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null



)