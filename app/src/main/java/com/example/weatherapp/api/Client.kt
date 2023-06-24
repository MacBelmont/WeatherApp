package com.example.weatherapp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class Client(private var retrofit: Retrofit) {

    fun build(): PreRequest{
        return PreRequest(retrofit)
    }

    fun getClient(): Client{
        retrofit = retrofit.newBuilder().client(init()).build()
        return this
    }

    private fun init(): OkHttpClient {
        val client = OkHttpClient.Builder()
        try{
            client.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder().header("Authorization", "")
                val newRequest = request.build()
                chain.proceed(newRequest)
            }
        } catch (e : Exception){
            e.printStackTrace()
        }

        client
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        return client.build()
    }

}