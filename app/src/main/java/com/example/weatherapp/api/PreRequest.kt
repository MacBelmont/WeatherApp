package com.example.weatherapp.api

import retrofit2.Retrofit

class PreRequest(private val retrofit: Retrofit) {

    fun userAPI(): UserAPI {
        return retrofit.create((UserAPI::class.java))
    }

    fun weatherAPI(): WeatherAPI {
        return retrofit.create((WeatherAPI::class.java))
    }

}