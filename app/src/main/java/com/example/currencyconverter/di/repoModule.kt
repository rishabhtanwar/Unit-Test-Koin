package com.example.currencyconverter.di

import com.example.currencyconverter.data.api.ApiHelper
import com.example.currencyconverter.data.api.ApiHelperImpl
import com.example.currencyconverter.data.repository.CurrencyRepo
import org.koin.dsl.module

val repoModule = module {
    factory { CurrencyRepo(get()) }
    factory <ApiHelper> { return@factory ApiHelperImpl(get(), get()) }
}