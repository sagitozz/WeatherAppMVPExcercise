package com.example.weatherappmvpexcercise.mvp

import com.example.weatherappmvpexcercise.mvp.base.IView
import com.example.weatherappmvpexcercise.network.dto.DataItem

interface MainActivityContract {

    interface Presenter {
        fun loadData() {}
    }

    interface View : IView {
        fun updateUi(list: List<DataItem>, dataItems : List<DataItem>)
        fun onError()
        fun updateCity(city_text: String)
//        fun updateCoordinates(latitude: Double, longitude: Double)
        fun buildGpsAlertDialog()
        fun setCurrentWeatherIcon(string : String)
        fun setFirstFutureWeatherIcon(string : String)
        fun setSecondFutureWeatherIcon(string : String)
    }
}
