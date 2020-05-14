package com.example.weatherappmvpexcercise.di

import com.example.weatherappmvpexcercise.IconReturner
import com.example.weatherappmvpexcercise.IconReturnerImpl
import com.example.weatherappmvpexcercise.ImageLoaderImpl
import org.koin.dsl.module


val imageModule = module {
    factory { ImageLoaderImpl() }
    factory <IconReturner> { IconReturnerImpl(get<ImageLoaderImpl>()) }
}
