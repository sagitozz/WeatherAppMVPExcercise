package com.example.weatherappmvpexcercise.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappmvpexcercise.R
import com.example.weatherappmvpexcercise.network.dto.DataItem

class WeatherRecyclerAdapter (var items : List<DataItem>) :
    RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.weather_item, parent, false))


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemDate =itemView.findViewById<TextView>(R.id.item_date)
        private val itemTemp = itemView.findViewById<TextView>(R.id.item_temp)

        fun bind(item: DataItem) {
            itemTemp.text = item.appTemp.toString()
            itemDate.text = item.datetime
        }
    }
}