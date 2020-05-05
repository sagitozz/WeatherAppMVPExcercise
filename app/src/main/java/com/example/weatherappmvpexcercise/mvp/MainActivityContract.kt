package com.example.weatherappmvpexcercise.mvp

import com.example.weatherappmvpexcercise.mvp.base.IView
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem

interface MainActivityContract {

    interface Presenter {
        fun loadWeatherData() {}
    }

    interface View : IView {
        fun updateUi(list: List<WeatherDataItem>, dataItems : List<WeatherDataItem>)
        fun onError()
        fun updateCity(city_text: String)
//        fun updateCoordinates(latitude: Double, longitude: Double)
        fun buildGpsAlertDialog()
        fun setCurrentWeatherIcon(string : String)
        fun setFirstFutureWeatherIcon(string : String)
        fun setSecondFutureWeatherIcon(string : String)
    }
}
