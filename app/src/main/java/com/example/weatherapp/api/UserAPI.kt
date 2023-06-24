package com.example.weatherapp.api

import com.example.weatherapp.models.ResponseData
import retrofit2.http.GET

interface UserAPI {

    @GET("dcbffd39-57b0-41b5-8ab3-a24d99e69b18")
    suspend fun obtenerUsuario(): ResponseData

}