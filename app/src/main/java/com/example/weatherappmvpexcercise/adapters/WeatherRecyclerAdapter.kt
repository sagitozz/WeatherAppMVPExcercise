package com.example.weatherappmvpexcercise.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappmvpexcercise.R
import com.example.weatherappmvpexcercise.constants.Constants.FIVE_DAYS_FORECAST_RECYCLER_SIZE
import com.example.weatherappmvpexcercise.network.dto.DataItem

class WeatherRecyclerAdapter(var items: MutableList<DataItem>) :
    RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
    )

    override fun getItemCount() = FIVE_DAYS_FORECAST_RECYCLER_SIZE

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemDate = itemView.findViewById<TextView>(R.id.itemDate)
        private val itemTemp = itemView.findViewById<TextView>(R.id.itemTemp)

        fun bind(item: DataItem) {
            itemTemp.text = item.appTemp.toInt().toString() + "°"
            itemDate.text = item.timestamp
        }
    }

//    //todo тут вообще какая-то ерунда! То что приходит в аругментах должно быть имутабельно! убрать MutableList
//    // скорее всего у тебя должна быть переменная MutableList<DataItem?>
//    // в самом адаптере в которую ты будешь добавлять нвоые элементы и тогда в getItemCount не будет 5
//    // мне не нравится nullable DataItem. Ты должен все null dataItem потерять задолго до попадания их в адаптер
//    fun update(weatherItems: MutableList<DataItem?>) {
//        Log.d(LOG_TAG, "adapter.clear")
//        weatherItems.clear()
//        Log.d(LOG_TAG, "adapter.addAll")
//        //todo ты вообще понимаешь что ты тут делаешь??? Что вообще делает весь твой метод???!!!
//        weatherItems.addAll(weatherItems)
//        notifyDataSetChanged()
//        Log.d(LOG_TAG, "adapter.NotifyDataSetChanged")
//    }
}
