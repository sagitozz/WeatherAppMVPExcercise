package com.example.weatherappmvpexcercise.di

import com.example.weatherappmvpexcercise.IconSetter
import com.example.weatherappmvpexcercise.IconSetterImpl
import com.example.weatherappmvpexcercise.ImageLoaderImpl
import org.koin.dsl.module


val imageModule = module {
    factory { ImageLoaderImpl() }
    factory <IconSetter> { IconSetterImpl(get<ImageLoaderImpl>()) }
}
