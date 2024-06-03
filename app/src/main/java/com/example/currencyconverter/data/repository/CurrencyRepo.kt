package com.example.currencyconverter.data.repository

import android.util.Log
import com.example.currencyconverter.data.api.ApiHelper
import com.example.currencyconverter.data.model.CurrencyListResponse
import com.example.currencyconverter.data.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CurrencyRepo(private val apiHelper: ApiHelper) {

    fun getLatestCurrencyResponse() = flow<Resource<CurrencyListResponse>> {
        emit(Resource.Loading())
        val response = apiHelper.getLatestCurrencyResponse()
        emit(response)
    }.catch {
        //Log.e("TAG", "getLatestCurrencyResponse: ${it.printStackTrace()}" )
        System.out.println(it.printStackTrace())
        emit(Resource.Error(error = "Something went wrong"))
    }
}