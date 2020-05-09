package com.example.weatherappmvpexcercise.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappmvpexcercise.IconReturnerImpl
import com.example.weatherappmvpexcercise.R
import com.example.weatherappmvpexcercise.Utils.Constants
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
        private val itemIcon: ImageView = itemView.findViewById(R.id.mainWeatherIcon)

        fun bind(item: WeatherDataItem) {
            itemTemp.text =
                itemView.context.getString(R.string.item_temp, item.appTemp.toInt().toString())
            reformatAndSetDate(item.datetime)
            itemPres.text = itemView.context.getString(
                R.string.pressure_view,
                ((item.pres) / Constants.PRESSURE_DIVIDER).toInt().toString()
            )
            itemWind.text =
                itemView.context.getString(R.string.wind_view, item.windSpd.toInt().toString())
            itemHumid.text = itemView.context.getString(R.string.humidity_view, item.rh.toString())
            setRecyclerWeatherIcon(item.weather.code.toString())
        }

        private fun setRecyclerWeatherIcon(string: String) {
            IconReturnerImpl.setIconIntoImageView(string, itemIcon)
        }

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        private fun reformatAndSetDate(date: String) {
            val inputDateFormat =
                SimpleDateFormat(Constants.INPUT_DATE_FORMAT, Locale.ENGLISH)
            val publishedDate: String = date
            val dateNew: Date = inputDateFormat.parse(publishedDate)
            val outputDateFormat =
                SimpleDateFormat(Constants.OUTPUT_DATE_FORMAT, Locale.getDefault())
            val output = outputDateFormat.format(dateNew)
            itemDate.text = output
        }
    }
}
