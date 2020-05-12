package com.example.weatherappmvpexcercise.di

import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import org.koin.dsl.module

val dataModule = module {
    single<DataService> { DataServiceImpl() }
    single { MainActivityPresenter(get()) }
}