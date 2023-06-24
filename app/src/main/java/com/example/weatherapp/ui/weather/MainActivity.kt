package com.example.weatherapp.ui.weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.interfaces.MainInterface
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.utils.TimeUtils.getTimeFromTimestapm
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), MainInterface {

    private val viewModel by viewModel<MainActivityViewModel>()

    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getUserLastKnownLocation()
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                getUserLastKnownLocation()
            }

            else -> {
                // No location access granted.
                onRefreshCall(null)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.mainInterface = this

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.tvGreeting.text = viewModel.getGreeting()

        getUser()
        checkLocationEnabled()


        binding.container.setOnRefreshListener {
            checkLocationPermission()
        }

    }

    fun checkLocationEnabled() {
        if (viewModel.checkLocationEnabled()) {
            checkLocationPermission()
        } else {
            Toast.makeText(
                this,
                "Tienes deshabilitada la ubicacion en tu dispositivo, por favor enciendela.",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    fun getUser() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUserFromService()
        }
    }

    private fun checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {

        }

    }

    fun getUserLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkLocationPermission()
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                onRefreshCall(location)
            }
    }

    override fun onRefreshCall(location: Location?) {
        binding.container.isEnabled = false
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeatherService(location)
        }
    }

    override fun onRefreshEnd() {
        binding.container.isRefreshing = false
        binding.container.isEnabled = true
        Toast.makeText(
            this,
            "Informacion Actualizada.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun updateUI(weatherResponse: WeatherResponse?) {
        var stringWe = Gson().toJson(weatherResponse)
        println("weatherRes: $stringWe")
        binding.tvWeatherCondition.text = weatherResponse?.weather?.get(0)?.main ?: ""
        binding.tvDegreeCondition.text = weatherResponse?.main?.humidity.toString() + "%"
        binding.tvTempLabel.text = weatherResponse?.main?.temp_min.toString() + "°C"
        binding.tvTempCondition.text = weatherResponse?.main?.temp_max.toString() + "°C"
        binding.tvWindCondition.text = weatherResponse?.wind?.speed.toString() + " Km/H"
        binding.tvCountryLabel.text = weatherResponse?.sys?.country
        binding.tvLocation.text = weatherResponse?.name
        binding.tvSunriseTime.text = getTimeFromTimestapm(weatherResponse?.sys?.sunrise)
        binding.tvSunsetTime.text = getTimeFromTimestapm(weatherResponse?.sys?.sunset)
    }
}