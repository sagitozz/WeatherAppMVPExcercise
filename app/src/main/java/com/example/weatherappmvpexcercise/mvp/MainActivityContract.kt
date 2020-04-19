package com.example.weatherappmvpexcercise.mvp

import com.example.weatherappmvpexcercise.mvp.base.IView
import com.example.weatherappmvpexcercise.network.dto.DataItem
import com.example.weatherappmvpexcercise.network.dto.Weather

interface MainActivityContract {

    interface Presenter {
        fun loadData() {}
    }

    interface View : IView {
        fun updateUi(list: MutableList<DataItem>)
        fun onError()
        fun updateCity(city_text: String)
        fun updateCoordinates(latitude: Double, longitude: Double)
    }
}
