package com.example.weatherappmvpexcercise

import com.example.weatherappmvpexcercise.Utils.TimeOfDay
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem

object FutureForecastSetter {

    fun setFutureForecastText(list: List<WeatherDataItem>, timeOfDay: TimeOfDay): Array<String> {
        lateinit var firstFutureText: String
        lateinit var secondFutureText: String
        when (timeOfDay) {
            TimeOfDay.MORNING -> {
                firstFutureText = App.applicationContext().getString(
                    R.string.morning_future_temperature_view,
                    list[2].temp.toInt().toString()
                )
                secondFutureText = App.applicationContext().getString(
                    R.string.evening_future_temperature_view,
                    list[4].temp.toInt().toString()
                )
            }
            TimeOfDay.DAYTIME -> {
                firstFutureText = App.applicationContext().getString(
                    R.string.evening_future_temperature_view,
                    list[2].temp.toInt().toString()
                )
                secondFutureText = App.applicationContext().getString(
                    R.string.night_future_temperature_view,
                    list[4].temp.toInt().toString()
                )
            }
            TimeOfDay.EVENING -> {
                firstFutureText = App.applicationContext().getString(
                    R.string.night_future_temperature_view,
                    list[2].temp.toInt().toString()
                )
                secondFutureText = App.applicationContext().getString(
                    R.string.morning_future_temperature_view,
                    list[4].temp.toInt().toString()
                )
            }
            TimeOfDay.NIGHT -> {
                firstFutureText = App.applicationContext().getString(
                    R.string.morning_future_temperature_view,
                    list[2].temp.toInt().toString()
                )
                secondFutureText = App.applicationContext().getString(
                    R.string.daytime_future_temperature_view,
                    list[4].temp.toInt().toString()
                )
            }
        }
        return arrayOf(firstFutureText, secondFutureText)
    }

    fun setFutureIconCode(list: List<WeatherDataItem>): Array<String> {
        val firstFutureIconCode = list[2].weather.code.toString()
        val secondFutureIconCode = list[4].weather.code.toString()
        return arrayOf(firstFutureIconCode, secondFutureIconCode)
    }
}