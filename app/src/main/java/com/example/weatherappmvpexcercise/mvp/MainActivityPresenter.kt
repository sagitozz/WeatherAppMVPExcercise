package com.example.weatherappmvpexcercise.mvp

import com.example.weatherappmvpexcercise.mvp.base.BasePresenter
import com.example.weatherappmvpexcercise.mvp.base.Model
import com.example.weatherappmvpexcercise.network.dto.DataItem
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter : BasePresenter<MainActivityContract.View>(), MainActivityContract.Presenter {
    val newsModel = Model()
    lateinit var dataItemList : List<DataItem>

    override fun loadData() {
            newsModel.modelGetWeather()?.enqueue( object : Callback<WeatherResponse?> {
        override fun onResponse(
            call: Call<WeatherResponse?>,
            response: Response<WeatherResponse?>
        ) {
            updateUi(response)

        }
                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    view?.onError()
                }
            })
    }

    fun updateUi (response: Response<WeatherResponse?>) {
        dataItemList = response.body()?.data ?: emptyList()
        view?.updateUi(dataItemList)
    }
}

