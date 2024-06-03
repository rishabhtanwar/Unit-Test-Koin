package com.example.currencyconverter.data.api

import com.example.currencyconverter.data.model.CurrencyListResponse
import com.example.currencyconverter.data.model.Resource
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/api/latest.json")
    fun getLatestCurrencyResponse():Deferred<Response<CurrencyListResponse>>
}