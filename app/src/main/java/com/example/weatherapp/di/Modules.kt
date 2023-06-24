package com.example.weatherapp.di

import com.example.weatherapp.api.Request
import com.example.weatherapp.repo.Repository
import com.example.weatherapp.ui.weather.MainActivityViewModel
import com.example.weatherapp.utils.LocationServicesUtils
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repo = module {
    factory { Repository() }
}

val services = module {
    factory { Request() }
}

val utils = module {
    single { LocationServicesUtils(get()) }
}

val viewModels = module {
    viewModel { MainActivityViewModel(get(), get()) }
}
