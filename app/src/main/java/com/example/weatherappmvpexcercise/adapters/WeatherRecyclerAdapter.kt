package com.example.weatherappmvpexcercise.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherappmvpexcercise.App
import com.example.weatherappmvpexcercise.R
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem
import java.text.SimpleDateFormat
import java.util.*

class WeatherRecyclerAdapter(private val items: List<WeatherDataItem>) :
    RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemDate = itemView.findViewById<TextView>(R.id.itemDate)
        private val itemTemp = itemView.findViewById<TextView>(R.id.itemTemp)
        private val itemPres = itemView.findViewById<TextView>(R.id.itemPres)
        private val itemWind = itemView.findViewById<TextView>(R.id.itemWind)
        private val itemHumid = itemView.findViewById<TextView>(R.id.itemHumid)
        private val itemIcon : ImageView = itemView.findViewById(R.id.weatherLogo)

        fun bind(item: WeatherDataItem) {
            itemTemp.text = App.instance.getString(R.string.item_temp, item.appTemp.toInt().toString())
            reformatAndSetDate(item.datetime)
            itemPres.text = App.instance.getString(
               R.string.pressure_view,
                ((item.pres) / Constants.PRESSURE_DIVIDER).toInt().toString())
            itemWind.text = App.instance.getString(R.string.wind_view, item.windSpd.toInt().toString())
            itemHumid.text = App.instance.getString(R.string.humidity_view, item.rh.toString())
            setRecyclerWeatherIcon(item.weather.code.toString())
        }

        fun setRecyclerWeatherIcon(string: String) {
            when (string) {
                App.instance.resources.getString(R.string.broken_clouds) -> {
                    glidingIntoRecycler(R.drawable.clouds)
                }
                App.instance.resources.getString(R.string.overcast_clouds) -> {
                    glidingIntoRecycler(R.drawable.clouds)
                }
                App.instance.resources.getString(R.string.few_clouds) -> {
                    glidingIntoRecycler(R.drawable.clouds)
                }
                App.instance.resources.getString(R.string.clear_sky) -> {
                    glidingIntoRecycler(R.drawable.sunny)
                }
                App.instance.resources.getString(R.string.almost_clear_sky) -> {
                    glidingIntoRecycler(R.drawable.sunny)
                }
                App.instance.resources.getString(R.string.local_clouds) -> {
                    glidingIntoRecycler(R.drawable.clouds)
                }
            }
        }

        private fun glidingIntoRecycler(drawable: Int) {
            Glide
                .with(App.applicationContext())
                .load(drawable)
                .into(itemIcon)
        }

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        private fun reformatAndSetDate(date: String) {
            val inputDateFormat =
                SimpleDateFormat(Constants.INPUT_DATE_FORMAT, Locale.ENGLISH)
            val publishedDate: String = date
            val dateNew : Date = inputDateFormat.parse(publishedDate)
            val outputDateFormat =
                SimpleDateFormat(Constants.OUTPUT_DATE_FORMAT, Locale.getDefault())
            val output = outputDateFormat.format(dateNew)
            itemDate.text = output
        }
    }
}
