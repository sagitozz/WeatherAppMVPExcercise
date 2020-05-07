package com.example.weatherappmvpexcercise.constants

object Constants {

    const val WEATHER_API_KEY = "76afd6b491msh5667831f19ac74dp15fa6bjsn58b7c52a950c"
    const val WEATHER_BASE_URL = "https://weatherbit-v1-mashape.p.rapidapi.com"
    const val COORDINATES_BASE_URL = "http://api.ipstack.com/"
    const val LOG_TAG = "MyLog"
    const val PRESSURE_DIVIDER: Double = 1.333224
    const val DIVIDER_FOR_GETTING_NEXT_DAY = 8
    const val LOCATION_REQUEST_INTERVAL = 10_000_000_000_000L
    const val LOCATION_REQUEST_FASTEST_INTERVAL = 30_000_000_000_000L
    const val INPUT_DATE_FORMAT = "yyyy-MM-dd:HH"
    const val OUTPUT_DATE_FORMAT = "d MMMM"
}
