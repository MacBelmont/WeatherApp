package com.example.weatherapp.interfaces

import android.location.Location
import com.example.weatherapp.models.WeatherResponse

interface MainInterface {
    fun onRefreshCall(location: Location?)
    fun onRefreshEnd()
    fun updateUI(weatherResponse: WeatherResponse?)
}