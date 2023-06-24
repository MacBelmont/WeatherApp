package com.example.weatherapp.api

import com.example.weatherapp.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    suspend fun obtenerClima(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appid: String, @Query("units") units: String): WeatherResponse
}