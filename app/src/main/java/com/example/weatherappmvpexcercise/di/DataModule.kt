@file:Suppress("RemoveExplicitTypeArguments")

package com.example.weatherappmvpexcercise.di

import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesRestApi
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherRestApi
import org.koin.dsl.module

val dataModule = module {
    factory<WeatherRestApi> { WeatherRestApi() }
    factory<CoordinatesRestApi> { CoordinatesRestApi() }
    factory<DataService> { DataServiceImpl(get<WeatherRestApi>(), get<CoordinatesRestApi>()) }
    factory<MainActivityPresenter> { MainActivityPresenter(get<DataService>()) }
}
