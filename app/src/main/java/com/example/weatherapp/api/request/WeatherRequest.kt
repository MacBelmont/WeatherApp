package com.example.weatherapp.api.request

data class WeatherRequest(
    val lat: Double,
    val lon: Double,
    val appid: String
)
