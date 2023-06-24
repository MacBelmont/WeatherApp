package com.example.weatherapp

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.example.weatherapp.di.*

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        setContext(this)

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    repo,
                    services,
                    utils,
                    viewModels
                )
            )
        }

    }

    companion object {
        private lateinit var context: Context

        fun setContext(context: Context) {
            this.context = context
        }

        fun getContext(): Context? {
            return context
        }


    }

}