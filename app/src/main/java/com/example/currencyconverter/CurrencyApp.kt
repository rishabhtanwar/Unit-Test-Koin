package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.di.appModule
import com.example.currencyconverter.di.repoModule
import com.example.currencyconverter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CurrencyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CurrencyApp)
            modules(arrayListOf(appModule, repoModule, viewModelModule))
        }
    }
}