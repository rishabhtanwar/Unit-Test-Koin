package com.example.currencyconverter.di

import com.example.currencyconverter.ui.MainViewModel
import com.example.currencyconverter.util.DefaultDispatcherProvider
import com.example.currencyconverter.util.DispatcherProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get(),get())
    }
single<DispatcherProvider> { return@single DefaultDispatcherProvider()  }
}