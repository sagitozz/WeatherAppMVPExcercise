package com.example.weatherappmvpexcercise

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappmvpexcercise.R.layout
import com.example.weatherappmvpexcercise.mvp.MainActivityContract
import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import com.example.weatherappmvpexcercise.network.dto.DataItem
import com.example.weatherappmvpexcercise.network.dto.Weather
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val mainActivityPresenter = MainActivityPresenter()

        mainActivityPresenter.attach(this)

    }

    override fun updateUi(list: List<DataItem>) {
        temperature.text = list[6].temp.toString() + "C"



    }

    override fun onError() {
        TODO("Not yet implemented")
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

