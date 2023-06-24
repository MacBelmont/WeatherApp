package com.example.weatherapp.api

import com.example.weatherapp.App.Companion.getContext
import com.example.weatherapp.R
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Request {

    private var retrofit: Retrofit? = null
    private var retrofitAlt: Retrofit? = null

    fun createWA(): Client {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(getContext()!!.getString(R.string.WEATHER_API_URL))
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().serializeNulls().create()
                    )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return Client(retrofit!!)
    }

    fun createUA(): Client {
        if (retrofitAlt == null) {
            retrofitAlt = Retrofit.Builder()
                .baseUrl(getContext()!!.getString(R.string.USER_API_URL))
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().serializeNulls().create()
                    )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return Client(retrofitAlt!!)
    }

}