package com.example.currencyconverter.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

val mockWebServerModule = module{
    factory { MockWebServer() }
}