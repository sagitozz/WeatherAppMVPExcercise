package com.example.weatherappmvpexcercise.mvp

import android.util.Log
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.mvp.base.BasePresenter
import com.example.weatherappmvpexcercise.mvp.base.Model
import com.example.weatherappmvpexcercise.network.dto.DataItem
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter : BasePresenter<MainActivityContract.View>(), MainActivityContract.Presenter {
    private val newsModel = Model()
    lateinit var dataItemList : List<DataItem>

    override fun loadData() {
            newsModel.modelGetWeather()?.enqueue( object : Callback<WeatherResponse?> {
        override fun onResponse(
            call: Call<WeatherResponse?>,
            response: Response<WeatherResponse?>
        ) {
            Log.d(Constants.LOG_TAG, "OnResponse презентера")
            updateUi(response)

        }
                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    view?.onError()
                }
            })
    }

    fun updateUi (response: Response<WeatherResponse?>) {
        Log.d(Constants.LOG_TAG, "updateUI презентера")

        dataItemList = response.body()?.data ?: emptyList()
        val city : String = response.body()?.city_name.toString()
        view?.updateCity(city)
        view?.updateUi(dataItemList)
    }
}

