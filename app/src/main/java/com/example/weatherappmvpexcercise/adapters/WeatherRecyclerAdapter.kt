package com.example.weatherappmvpexcercise.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappmvpexcercise.IconSetter
import com.example.weatherappmvpexcercise.R
import com.example.weatherappmvpexcercise.Utils.Constants
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem
import kotlinx.android.synthetic.main.weather_item.view.*
import org.koin.core.KoinComponent
import java.text.SimpleDateFormat
import java.util.*

class WeatherRecyclerAdapter(
    private val items: List<WeatherDataItem>,
    private val iconSetter: IconSetter
) : RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>(), KoinComponent {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], iconSetter)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), KoinComponent {

        private val itemDate = itemView.itemDate
        private val itemTemp = itemView.itemTemp
        private val itemPres = itemView.itemPres
        private val itemWind = itemView.itemWind
        private val itemHumid = itemView.itemHumid
        private val itemIcon = itemView.mainWeatherIcon

        fun bind(item: WeatherDataItem, iconSetter: IconSetter) {
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
            iconSetter.setIconIntoImageView(item.weather.code.toString(), itemIcon)
        }


        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        private fun reformatAndSetDate(date: String) {
            val inputDateFormat =
                SimpleDateFormat(Constants.INPUT_DATE_FORMAT, Locale.ENGLISH)
            val publishedDate: String = date
            val dateNew: Date = inputDateFormat.parse(publishedDate)
            val outputDateFormat =
                SimpleDateFormat(Constants.OUTPUT_DATE_FORMAT, Locale.getDefault())
            itemDate.text = outputDateFormat.format(dateNew)
        }
    }
}
