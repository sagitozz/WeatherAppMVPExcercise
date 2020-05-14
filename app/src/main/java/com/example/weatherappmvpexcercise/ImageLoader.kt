package com.example.weatherappmvpexcercise

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide


interface ImageLoader {

    fun loadImage(drawableRes: Int, intoView: ImageView)
}

class ImageLoaderImpl : ImageLoader{

    override fun loadImage(@DrawableRes drawableRes: Int, intoView: ImageView) {
        Glide
            .with(App.applicationContext())
            .load(drawableRes)
            .into(intoView)
    }
}
