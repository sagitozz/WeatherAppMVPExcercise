package com.example.weatherappmvpexcercise

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @author s.buvaka
 */
interface ImageLoader {

    fun loadImage(drawableRes: Int, intoView: ImageView)
}

class ImageLoaderImpl : ImageLoader {

    override fun loadImage(drawableRes: Int, intoView: ImageView) {
        Glide
            .with(App.applicationContext())
            .load(drawableRes)
            .into(intoView)
    }
}
