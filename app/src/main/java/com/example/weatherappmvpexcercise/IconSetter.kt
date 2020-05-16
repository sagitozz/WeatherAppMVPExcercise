package com.example.weatherappmvpexcercise

import android.widget.ImageView
import androidx.annotation.StringRes
import org.koin.core.KoinComponent

interface IconSetter {

    fun setIconIntoImageView(string: String, intoView: ImageView)
    fun getResourceName(stringResource: Int): String
}

class IconSetterImpl(private val imageLoader: ImageLoader) : IconSetter, KoinComponent {

    override fun setIconIntoImageView(string: String, intoView: ImageView) {
        when (string) {
            getResourceName(R.string.thunderstorm_with_light_rain) ->
                imageLoader.loadImage(R.drawable.ic_flash, intoView)
            getResourceName(R.string.thunderstorm_with_rain) ->
                imageLoader.loadImage(R.drawable.ic_flash, intoView)
            getResourceName(R.string.thunderstorm_with_heavy_rain) ->
                imageLoader.loadImage(R.drawable.ic_flash, intoView)
            getResourceName(R.string.thunderstorm_with_light_drizzle) ->
                imageLoader.loadImage(R.drawable.ic_flash, intoView)
            getResourceName(R.string.thunderstorm_with_drizzle) ->
                imageLoader.loadImage(R.drawable.ic_flash, intoView)
            getResourceName(R.string.thunderstorm_with_heavy_drizzle) ->
                imageLoader.loadImage(R.drawable.ic_flash, intoView)
            getResourceName(R.string.thunderstorm_with_hail) ->
                imageLoader.loadImage(R.drawable.ic_flash, intoView)
            getResourceName(R.string.drizzle) ->
                imageLoader.loadImage(R.drawable.ic_grad, intoView)
            getResourceName(R.string.light_drizzle) ->
                imageLoader.loadImage(R.drawable.ic_grad, intoView)
            getResourceName(R.string.heavy_drizzle) ->
                imageLoader.loadImage(R.drawable.ic_grad, intoView)
            getResourceName(R.string.light_rain) ->
                imageLoader.loadImage(R.drawable.ic_light_rain_sun, intoView)
            getResourceName(R.string.moderate_rain) ->
                imageLoader.loadImage(R.drawable.ic_heavy_rain, intoView)
            getResourceName(R.string.heavy_rain) ->
                imageLoader.loadImage(R.drawable.ic_heavy_rain, intoView)
            getResourceName(R.string.freezing_rain) ->
                imageLoader.loadImage(R.drawable.ic_heavy_rain, intoView)
            getResourceName(R.string.light_shower_rain) ->
                imageLoader.loadImage(R.drawable.ic_heavyest_rain, intoView)
            getResourceName(R.string.shower_rain) ->
                imageLoader.loadImage(R.drawable.ic_heavyest_rain, intoView)
            getResourceName(R.string.heavy_shower_rain) ->
                imageLoader.loadImage(R.drawable.ic_heavyest_rain, intoView)
            getResourceName(R.string.light_snow) ->
                imageLoader.loadImage(R.drawable.ic_snow, intoView)
            getResourceName(R.string.snow) ->
                imageLoader.loadImage(R.drawable.ic_snow, intoView)
            getResourceName(R.string.heavy_snow) ->
                imageLoader.loadImage(R.drawable.ic_heavy_snow, intoView)
            getResourceName(R.string.mix_snow_rain) ->
                imageLoader.loadImage(R.drawable.ic_heavy_snow, intoView)
            getResourceName(R.string.sleet) ->
                imageLoader.loadImage(R.drawable.ic_wind, intoView)
            getResourceName(R.string.heavy_sleet) ->
                imageLoader.loadImage(R.drawable.ic_wind, intoView)
            getResourceName(R.string.snow_shower) ->
                imageLoader.loadImage(R.drawable.ic_snow, intoView)
            getResourceName(R.string.heavy_snow_shower) ->
                imageLoader.loadImage(R.drawable.ic_snow, intoView)
            getResourceName(R.string.flurries) ->
                imageLoader.loadImage(R.drawable.ic_wind, intoView)
            getResourceName(R.string.mist) ->
                imageLoader.loadImage(R.drawable.ic_fog, intoView)
            getResourceName(R.string.smoke) ->
                imageLoader.loadImage(R.drawable.ic_fog, intoView)
            getResourceName(R.string.haze) ->
                imageLoader.loadImage(R.drawable.ic_fog, intoView)
            getResourceName(R.string.sand_dust) ->
                imageLoader.loadImage(R.drawable.ic_fog, intoView)
            getResourceName(R.string.fog) ->
                imageLoader.loadImage(R.drawable.ic_fog, intoView)
            getResourceName(R.string.freezing_fog) ->
                imageLoader.loadImage(R.drawable.ic_fog, intoView)
            getResourceName(R.string.clear_sky) ->
                imageLoader.loadImage(R.drawable.ic_sunny, intoView)
            getResourceName(R.string.few_clouds) ->
                imageLoader.loadImage(R.drawable.ic_sun_with_clouds, intoView)
            getResourceName(R.string.broken_cloud) ->
                imageLoader.loadImage(R.drawable.ic_sun_with_clouds, intoView)
            getResourceName(R.string.scattered_clouds) ->
                imageLoader.loadImage(R.drawable.ic_sun_with_clouds, intoView)
            getResourceName(R.string.overcast_clouds) ->
                imageLoader.loadImage(R.drawable.ic_cloud, intoView)
            getResourceName(R.string.unknown_precipitation) ->
                imageLoader.loadImage(R.drawable.ic_heavyest_rain, intoView)
        }
    }

    override fun getResourceName(@StringRes stringResource: Int): String {
        return App.applicationContext().resources.getString(stringResource)
    }
}


