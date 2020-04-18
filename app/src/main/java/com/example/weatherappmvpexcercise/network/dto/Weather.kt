package com.example.weatherappmvpexcercise.network.dto

import com.google.gson.annotations.SerializedName

data class Weather(
    //todo Ты либо все @SerializadName пиши в одну строку с переменной
    // как в DataItem либо все как тут. Придерживайся единого кодстайла

    @SerializedName("code")
    val code: String? = null,

    @SerializedName("icon")
    val icon: String? = null,

    @SerializedName("description")
    val description: String? = null
//todo тоже пробелы!!!

)
