package com.example.weatherappmvpexcercise.constants

enum class TimeOfDay(val array: Array<String>) {
    MORNING (arrayOf("06", "07", "08", "09", "10", "11")),
    DAYTIME (arrayOf("12", "13", "14", "15", "16", "17")),
    EVENING (arrayOf("18", "19", "20", "21", "22", "23")),
    NIGHT (arrayOf("00", "01", "02", "03", "04", "05"))
}