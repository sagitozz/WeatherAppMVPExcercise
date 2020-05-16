package com.example.weatherappmvpexcercise.Utils

import com.example.weatherappmvpexcercise.Utils.TimeOfDay.*
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    val morning = arrayOf("06", "07", "08", "09", "10", "11")
    val day = arrayOf("12", "13", "14", "15", "16", "17")
    val evening = arrayOf("18", "19", "20", "21", "22", "23")
    val night = arrayOf("00", "01", "02", "03", "04", "05")

//    fun getCurrentTimeOfDay(): TimeOfDay {
//        lateinit var timeOfDay: TimeOfDay
//        when (getCurrentDate().substringBefore(':')) {
//            in morning -> timeOfDay = MORNING
//            in day -> timeOfDay = DAYTIME
//            in evening -> timeOfDay = EVENING
//            in night -> timeOfDay = NIGHT
//        }
//        return timeOfDay
//    }

    fun getCurrentTimeOfDay(): TimeOfDay =
        when (getCurrentDate().substringBefore(':')) {
            in morning -> MORNING
            in day -> DAYTIME
            in evening ->  EVENING
            in night -> NIGHT
            else -> MORNING
        }

    fun getCurrentDate(): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeText = timeFormat.format(Date())
        return timeText
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun reformatAndSetDate(list: List<WeatherDataItem>) : String {
        val inputDateFormat =
            SimpleDateFormat(Constants.INPUT_DATE_FORMAT, Locale.ENGLISH)
        val publishedDate: String = list.first().datetime
        val dateNew: Date = inputDateFormat.parse(publishedDate)
        val outputDateFormat =
            SimpleDateFormat(Constants.OUTPUT_DATE_FORMAT, Locale.getDefault())
        return outputDateFormat.format(dateNew)
    }
}