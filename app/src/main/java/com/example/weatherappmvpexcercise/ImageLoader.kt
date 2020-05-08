package com.example.weatherappmvpexcercise

import android.widget.ImageView
import com.bumptech.glide.Glide


interface ImageLoader {

    fun loadImage(drawableRes: Int, intoView: ImageView)
    fun glideInto(string: String, intoView: ImageView)
    fun getResourceName(stringResource: Int) : String
}

class ImageLoaderImpl : ImageLoader {


    override fun glideInto(string: String, intoView: ImageView) {
        when (string) {

            getResourceName(R.string.thunderstorm_with_light_rain) -> {
                loadImage(R.drawable.ic_flash, intoView)
            }
            getResourceName(R.string.thunderstorm_with_rain) -> {
                loadImage(R.drawable.ic_flash, intoView)
            }
            getResourceName(R.string.thunderstorm_with_heavy_rain) -> {
                loadImage(R.drawable.ic_flash, intoView)
            }
            getResourceName(R.string.thunderstorm_with_light_drizzle) -> {
                loadImage(R.drawable.ic_flash, intoView)
            }
            getResourceName(R.string.thunderstorm_with_drizzle) -> {
                loadImage(R.drawable.ic_flash, intoView)
            }
            getResourceName(R.string.thunderstorm_with_heavy_drizzle) -> {
                loadImage(R.drawable.ic_flash, intoView)
            }
            getResourceName(R.string.thunderstorm_with_hail) -> {
                loadImage(R.drawable.ic_flash, intoView)
            }
            getResourceName(R.string.drizzle) -> {
                loadImage(R.drawable.ic_grad, intoView)
            }
            getResourceName(R.string.light_drizzle) -> {
                loadImage(R.drawable.ic_grad, intoView)
            }
            getResourceName(R.string.heavy_drizzle) -> {
                loadImage(R.drawable.ic_grad, intoView)
            }
            getResourceName(R.string.light_rain) -> {
                loadImage(R.drawable.ic_light_rain_sun, intoView)
            }
            getResourceName(R.string.moderate_rain) -> {
                loadImage(R.drawable.ic_heavy_rain, intoView)
            }
            getResourceName(R.string.heavy_rain) -> {
                loadImage(R.drawable.ic_heavy_rain, intoView)
            }
            getResourceName(R.string.freezing_rain) -> {
                loadImage(R.drawable.ic_heavy_rain, intoView)
            }
            getResourceName(R.string.moderate_rain) -> {
                loadImage(R.drawable.ic_heavy_rain, intoView)
            }
            getResourceName(R.string.light_shower_rain) -> {
                loadImage(R.drawable.ic_heavyest_rain, intoView)
            }
            getResourceName(R.string.shower_rain) -> {
                loadImage(R.drawable.ic_heavyest_rain, intoView)
            }
            getResourceName(R.string.heavy_shower_rain) -> {
                loadImage(R.drawable.ic_heavyest_rain, intoView)
            }
            getResourceName(R.string.light_snow) -> {
                loadImage(R.drawable.ic_snow, intoView)
            }
            getResourceName(R.string.snow) -> {
                loadImage(R.drawable.ic_snow, intoView)
            }
            getResourceName(R.string.heavy_snow) -> {
                loadImage(R.drawable.ic_heavy_snow, intoView)
            }
            getResourceName(R.string.mix_snow_rain) -> {
                loadImage(R.drawable.ic_heavy_snow, intoView)
            }
            getResourceName(R.string.sleet) -> {
                loadImage(R.drawable.ic_wind, intoView)
            }
            getResourceName(R.string.heavy_sleet) -> {
                loadImage(R.drawable.ic_wind, intoView)
            }
            getResourceName(R.string.snow_shower) -> {
                loadImage(R.drawable.ic_snow, intoView)
            }
            getResourceName(R.string.heavy_snow_shower) -> {
                loadImage(R.drawable.ic_snow, intoView)
            }
            getResourceName(R.string.flurries) -> {
                loadImage(R.drawable.ic_wind, intoView)
            }
            getResourceName(R.string.mist) -> {
                loadImage(R.drawable.ic_fog, intoView)
            }
            getResourceName(R.string.smoke) -> {
                loadImage(R.drawable.ic_fog, intoView)
            }
            getResourceName(R.string.haze) -> {
                loadImage(R.drawable.ic_fog, intoView)
            }
            getResourceName(R.string.sand_dust) -> {
                loadImage(R.drawable.ic_fog, intoView)
            }
            getResourceName(R.string.fog) -> {
                loadImage(R.drawable.ic_fog, intoView)
            }
            getResourceName(R.string.freezing_fog) -> {
                loadImage(R.drawable.ic_fog, intoView)
            }
            getResourceName(R.string.clear_sky) -> {
                loadImage(R.drawable.ic_sunny, intoView)
            }
            getResourceName(R.string.few_clouds) -> {
                loadImage(R.drawable.ic_sun_with_clouds, intoView)
            }
            getResourceName(R.string.broken_cloud) -> {
                loadImage(R.drawable.ic_sun_with_clouds, intoView)
            }
            getResourceName(R.string.scattered_clouds) -> {
                loadImage(R.drawable.ic_sun_with_clouds, intoView)
            }
            getResourceName(R.string.overcast_clouds) -> {
                loadImage(R.drawable.ic_cloud, intoView)
            }
            getResourceName(R.string.unknown_precipitation) -> {
                loadImage(R.drawable.ic_heavyest_rain, intoView)
            }
        }
    }

    override fun getResourceName(stringResource: Int) : String {
        return App.applicationContext().resources.getString(stringResource)
    }


    override fun loadImage(drawableRes: Int, intoView: ImageView) {
        Glide
            .with(App.applicationContext())
            .load(drawableRes)
            .into(intoView)
    }

}
