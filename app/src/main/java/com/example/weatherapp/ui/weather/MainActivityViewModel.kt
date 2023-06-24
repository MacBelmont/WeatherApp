package com.example.weatherapp.ui.weather

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.interfaces.MainInterface
import com.example.weatherapp.models.ResponseData
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.repo.Repository
import com.example.weatherapp.utils.LocationServicesUtils
import com.google.gson.Gson
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class MainActivityViewModel(
    private val repository: Repository,
    private val locationServices: LocationServicesUtils
): ViewModel() {

    var mainInterface: MainInterface? = null
    var weatherRes: WeatherResponse? = null
    var user: ResponseData? = null

    fun getGreeting(): String {
        val sdf = SimpleDateFormat("HH")
        var greeting = "Hello!"
        try {
            val currentTime = sdf.format(Date()).toInt()
            if(currentTime in 5..11){
                greeting = "Good morning!"
            } else if (currentTime in 12 .. 19) {
                greeting = "Good afternoon!"
            } else {
                greeting = "Good night!"
            }

        } catch (e: Exception){
            e.printStackTrace()
        }
        return  greeting
    }

    suspend fun getUserFromService(){
        user = repository.makeUserAPICall()
        var stringUsr = Gson().toJson(user)
        println("user: $stringUsr")
    }

    suspend fun getWeatherService(location: Location?){

        //Default Lat Lon when it's null
        var latitude = 18.062312
        var longitude = -98.437730

        if (location != null){
            latitude = location.latitude
            longitude = location.longitude
        }

        weatherRes = repository.makeWeatherAPICall(latitude, longitude, "787546c4ce3b2639a48674d12606e8e7")
        mainInterface?.onRefreshEnd()
        mainInterface?.updateUI(weatherRes)
    }

    fun checkLocationEnabled(): Boolean{
        return locationServices.isLocationEnabled()
    }


}