package com.example.weatherapp.utils

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

object TimeUtils {

    fun getTimeFromTimestapm(timestamp: Long?) :String {
        if(timestamp != null) {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = timestamp * 1000L
            val timeFormat = DateFormat.format("HH:mm", calendar).toString()
            return "$timeFormat HRS"
        } else {
            return "N/A"
        }
    }

}