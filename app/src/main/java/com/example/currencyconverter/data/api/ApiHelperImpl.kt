package com.example.currencyconverter.data.api

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.currencyconverter.data.model.CurrencyListResponse
import com.example.currencyconverter.data.model.Resource
import com.example.currencyconverter.util.Constant
import com.google.gson.Gson
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ApiHelperImpl(private val apiService: ApiService,private val pref:SharedPreferences) : ApiHelper {

    override suspend fun getLatestCurrencyResponse():Resource<CurrencyListResponse> {
        val apiSavedTime = pref.getLong(Constant.SAVED_API_RESPONSE_TIME,0)
        val apiSavedResponse = pref.getString(Constant.SAVED_API_RESPONSE,null)
        //Log.e("TAG", "Time Diff: ${System.currentTimeMillis()} and $apiSavedTime and ${TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()-apiSavedTime)}")
        if (!apiSavedResponse.isNullOrEmpty()&&TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()-apiSavedTime)<30){
            //Log.e("TAG", "stale response: $apiSavedResponse")
            val staleResponse = Gson().fromJson(apiSavedResponse,CurrencyListResponse::class.java)
            return Resource.Success(staleResponse)
        }
       val response = apiService.getLatestCurrencyResponse().await()
        val responseBody = response.body()
        System.out.println("response in api = ${responseBody}")
        //Log.e("TAG", "getLatestCurrencyResponse: ${response.toString()}")
        return if (response.isSuccessful){
            Resource.Success(responseBody)
        }else{
            Resource.Error("Something went wrong")
        }
    }
}