package com.example.weatherappmvpexcercise.mvp

import com.example.weatherappmvpexcercise.mvp.base.IView
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem

interface MainActivityContract {

    interface Presenter {
        fun loadWeatherData() {}
    }

    interface View : IView {
        fun updateUi(listForRecycler: List<WeatherDataItem>, listForMainView: List<WeatherDataItem>)
        fun onError()
        fun updateCity(city_text: String)
        fun buildGpsAlertDialog()
        fun setCurrentWeatherIcon(string: String)
    }
}
