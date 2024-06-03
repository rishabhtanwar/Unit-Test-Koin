package com.example.currencyconverter.di

import com.example.currencyconverter.data.api.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun testNetworkModule(baseUrl:String) = module{
    single { provideTestRetrofit(baseUrl) }
    single { provideTestApiService(get()) }
}

fun provideTestRetrofit(baseUrl: String): Retrofit =
    Retrofit.Builder().baseUrl(baseUrl)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create()).build()

fun provideTestApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)