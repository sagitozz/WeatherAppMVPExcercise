package com.example.weatherappmvpexcercise

import com.example.weatherappmvpexcercise.Utils.TimeOfDay
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem

object FutureForecastSetter {

    fun getFutureForecastText(items: List<WeatherDataItem>, timeOfDay: TimeOfDay): Pair<String, String> {
        lateinit var firstFutureText: String
        lateinit var secondFutureText: String
        when (timeOfDay) {
            TimeOfDay.MORNING -> {
                firstFutureText = App.applicationContext().getString(
                    R.string.daytime_future_temperature_view,
                    items[2].temp.toInt().toString()
                )
                secondFutureText = App.applicationContext().getString(
                    R.string.evening_future_temperature_view,
                    items[4].temp.toInt().toString()
                )
            }
            TimeOfDay.DAYTIME -> {
                firstFutureText = App.applicationContext().getString(
                    R.string.evening_future_temperature_view,
                    items[2].temp.toInt().toString()
                )
                secondFutureText = App.applicationContext().getString(
                    R.string.night_future_temperature_view,
                    items[4].temp.toInt().toString()
                )
            }
            TimeOfDay.EVENING -> {
                firstFutureText = App.applicationContext().getString(
                    R.string.night_future_temperature_view,
                    items[2].temp.toInt().toString()
                )
                secondFutureText = App.applicationContext().getString(
                    R.string.morning_future_temperature_view,
                    items[4].temp.toInt().toString()
                )
            }
            TimeOfDay.NIGHT -> {
                firstFutureText = App.applicationContext().getString(
                    R.string.morning_future_temperature_view,
                    items[2].temp.toInt().toString()
                )
                secondFutureText = App.applicationContext().getString(
                    R.string.daytime_future_temperature_view,
                    items[4].temp.toInt().toString()
                )
            }
        }
        return Pair(firstFutureText, secondFutureText)
    }

    fun getFutureIconCode(items: List<WeatherDataItem>): Pair<String, String> {
        return Pair(items[2].weather.code.toString(), items[4].weather.code.toString())
    }
}