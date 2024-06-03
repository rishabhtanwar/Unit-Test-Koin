package com.example.currencyconverter.data.model

sealed class Resource<T> {
    data class Success<T>(val data:T?): Resource<T>()
    data class Error<T>(val error:String):Resource<T>()
    data class Loading<T>(val message:String="Loading...") : Resource<T>()

    val extractData: T?
        get() = when (this) {
            is Success -> data
            is Error -> null
            is Loading -> null
        }
}