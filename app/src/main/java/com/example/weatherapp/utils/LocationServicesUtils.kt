package com.example.weatherapp.utils

import android.content.Context
import android.location.LocationManager

class LocationServicesUtils(val appContext: Context) {

    fun isLocationEnabled(): Boolean {

        // Esta function provee accesso a los servicios de ubicacion del sistema.
        val locationManager: LocationManager =
            appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}