package com.example.weatherappmvpexcercise

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.weatherappmvpexcercise.di.DataService
import org.koin.core.KoinComponent
import org.koin.core.inject


interface ImageLoader {

    fun loadImage(drawableRes: Int, intoView: ImageView)
}

class ImageLoaderImpl : ImageLoader, KoinComponent{

    private val service : DataService by inject()

    override fun loadImage(@DrawableRes drawableRes: Int, intoView: ImageView) {
        Glide
            .with(App.applicationContext())
            .load(drawableRes)
            .into(intoView)
    }
}
