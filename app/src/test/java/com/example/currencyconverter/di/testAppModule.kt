package com.example.currencyconverter.di

fun configureTestAppModules(testBaseUrl: String) =
    listOf(testNetworkModule(testBaseUrl), testRepoModule, testViewModelModule, mockWebServerModule)