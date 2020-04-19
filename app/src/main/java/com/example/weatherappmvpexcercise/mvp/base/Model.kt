package com.example.weatherappmvpexcercise.mvp.base

import android.util.Log
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.network.dto.RestApi
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import retrofit2.Call

class Model {

    private val restApi: RestApi? = RestApi()

    //todo в котлине можно делать вот так
    //    fun modelGetWeather(latitude: Double, longitude: Double): Call<WeatherResponse?>? =
    //        restApi?.getEndPoint()?.getWeather(latitude, longitude)
    // но у тебя тут еще логирование, так что у тебя норм, это больше для общего развития
    // ***
    // еще мне не очень нравится что у тебя WeatherResponse nullable. Ну и с респонсом тоже стоит подумать
    // но это просто подумать, если не найдешь способо избваиться то не страшно
    fun modelGetWeather(latitude: Double, longitude: Double): Call<WeatherResponse?>? {
        Log.d(Constants.LOG_TAG, "Запрос из модели")
        return restApi?.getEndPoint()?.getWeather(latitude, longitude)
    }
}
