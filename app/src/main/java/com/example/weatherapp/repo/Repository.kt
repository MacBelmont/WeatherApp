package com.example.weatherapp.repo

import com.example.weatherapp.api.Request
import com.example.weatherapp.models.ResponseData
import com.example.weatherapp.models.WeatherResponse

class Repository {

    suspend fun makeWeatherAPICall(lat: Double, lon: Double, appid: String, throwException: Boolean = false): WeatherResponse?{
        return Request().createWA().getClient().build().weatherAPI()
            .obtenerClima(lat,lon, appid, "metric")
    }

    suspend fun makeUserAPICall(): ResponseData? {
        return Request().createUA().getClient().build().userAPI()
            .obtenerUsuario()
    }

}