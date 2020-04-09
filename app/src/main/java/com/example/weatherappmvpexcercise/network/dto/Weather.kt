package com.example.weatherappmvpexcercise.network.dto

import com.google.gson.annotations.SerializedName

data class Weather(

	@SerializedName("code")
	val code: String? = null,

	@SerializedName("icon")
	val icon: String? = null,

	@SerializedName("description")
	val description: String? = null



)
