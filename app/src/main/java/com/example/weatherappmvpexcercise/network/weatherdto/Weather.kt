package com.example.weatherappmvpexcercise.network.weatherdto

import com.google.gson.annotations.SerializedName

data class Weather(

    @SerializedName("code") val code: String? = null,
    @SerializedName("icon") val icon: String? = null,
    @SerializedName("description") val description: String? = null
)
