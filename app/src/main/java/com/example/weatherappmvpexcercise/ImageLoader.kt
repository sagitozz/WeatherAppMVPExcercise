package com.example.weatherappmvpexcercise

import android.widget.ImageView
import com.bumptech.glide.Glide


interface ImageLoader {

    fun loadImage(drawableRes: Int, intoView: ImageView)
    fun glideInto(string: String, intoView: ImageView)
}

class ImageLoaderImpl : ImageLoader {


    override fun glideInto(string: String, intoView: ImageView) {
        when (string) {
            App.applicationContext().resources.getString(R.string.broken_clouds) -> {
                loadImage(R.drawable.clouds, intoView)
            }
            App.applicationContext().resources.getString(R.string.overcast_clouds) -> {
                loadImage(R.drawable.clouds, intoView)
            }
            App.applicationContext().resources.getString(R.string.few_clouds) -> {
                loadImage(R.drawable.clouds, intoView)
            }
            App.applicationContext().resources.getString(R.string.clear_sky) -> {
                loadImage(R.drawable.sunny, intoView)
            }
            App.applicationContext().resources.getString(R.string.local_clouds) -> {
                loadImage(R.drawable.clouds, intoView)
            }
            App.applicationContext().resources.getString(R.string.almost_clear_sky) -> {
                loadImage(R.drawable.sunny, intoView)
            }
        }
    }

    override fun loadImage(drawableRes: Int, intoView: ImageView) {
        Glide
            .with(App.applicationContext())
            .load(drawableRes)
            .into(intoView)
    }

}
