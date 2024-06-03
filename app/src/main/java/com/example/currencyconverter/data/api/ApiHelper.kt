package com.example.currencyconverter.data.api

import com.example.currencyconverter.data.model.CurrencyListResponse
import com.example.currencyconverter.data.model.Resource
import retrofit2.Response

interface ApiHelper {
   suspend fun getLatestCurrencyResponse():Resource<CurrencyListResponse>
}