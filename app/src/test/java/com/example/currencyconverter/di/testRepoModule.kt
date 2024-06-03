package com.example.currencyconverter.di

import com.example.currencyconverter.data.api.ApiHelper
import com.example.currencyconverter.data.api.ApiHelperImpl
import com.example.currencyconverter.data.repository.CurrencyRepo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val testRepoModule = module{
    single { getSharePref(androidContext()) }
    factory { CurrencyRepo(get()) }
    factory <ApiHelper> { return@factory ApiHelperImpl(get(), get()) }
}