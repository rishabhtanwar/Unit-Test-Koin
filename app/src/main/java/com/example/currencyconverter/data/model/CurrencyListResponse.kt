package com.example.currencyconverter.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyListResponse(
    @SerializedName("base")
    val base: String?=null,
    @SerializedName("disclaimer")
    val disclaimer: String?=null,
    @SerializedName("license")
    val license: String?=null,
    @SerializedName("timestamp")
    val timestamp: Int?=null,
    @SerializedName("rates")
    val rates: HashMap<String,Double?>?=null
)

data class CountryWisePrice(
    val price:Double?,
    val name:String
)