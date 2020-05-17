package com.example.weatherappmvpexcercise

import android.app.Application
import android.content.Context
import com.example.weatherappmvpexcercise.di.dataModule
import com.example.weatherappmvpexcercise.di.imageModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger(Level.INFO)
            modules(dataModule, imageModule)
        }
    }

    companion object {
        lateinit var instance: App

        fun applicationContext(): Context = instance.applicationContext
    }
}
