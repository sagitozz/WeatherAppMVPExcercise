package com.example.weatherappmvpexcercise

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappmvpexcercise.R.layout
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.mvp.MainActivityContract
import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import com.example.weatherappmvpexcercise.network.dto.DataItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val mainActivityPresenter = MainActivityPresenter()
        mainActivityPresenter.attach(this)
        mainActivityPresenter.loadData()
    }

    override fun updateUi(list: List<DataItem>) {
        Log.d(Constants.LOG_TAG, "updateUI View")

        temperature.text = list[6].appTemp.toString() + "C"
        pressure.text = list[6].pres.toString()
        wind.text = list[6]?.windSpd.toString()
        date.text = list[6]?.datetime
        humidity.text = list[6].rh.toString()
    }

    override fun updateCity(city_text : String) {
        city.text = city_text
    }

    override fun onError() {
        temperature.text= "ON ERROR"
    }

    override fun showLoader() {
        TODO("Not yet implemented")
    }

    override fun hideLoader() {
        TODO("Not yet implemented")
    }

    override fun handleError(error: String) {
        TODO("Not yet implemented")
    }
}

