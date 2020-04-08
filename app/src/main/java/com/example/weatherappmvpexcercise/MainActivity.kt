package com.example.weatherappmvpexcercise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappmvpexcercise.R.layout
import com.example.weatherappmvpexcercise.mvp.base.Model
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val newsModel = Model()
            newsModel.modelGetWeather()?.enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    weather.text =
                        response.body()?.data?.get(3).toString() // КАКАЯТО ХУЕТА НАДО  СДЕЛАТЬ ЧТОБА ПРИХОДИЛ АЙТЕМ И ДЕРНУТЬ ЕГО ПОЛЕ А НЕ ТЕЛО РЕСПОНСА
                }

                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

