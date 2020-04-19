package com.example.weatherappmvpexcercise

import android.app.Application
import android.content.Context


class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {

        lateinit var instance: App

        fun applicationContext(): Context = instance.applicationContext
    }
}