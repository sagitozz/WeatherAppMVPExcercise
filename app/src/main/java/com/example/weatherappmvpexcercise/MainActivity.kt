package com.example.weatherappmvpexcercise

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappmvpexcercise.R.*
import com.example.weatherappmvpexcercise.R.id.*
import com.example.weatherappmvpexcercise.mvp.base.Model
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        weather.text = ""
        val newsModel = Model()


            newsModel.modelGetWeather()?.enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    weather.text = response.body().toString() // КАКАЯТО ХУЕТА, НАДО  СДЕЛАТЬ ЧТОБА ПРИХОДИЛ АЙТЕМ А НЕ ТЕЛО РЕСПОНСА
                }

                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

