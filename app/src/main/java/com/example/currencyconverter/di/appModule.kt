package com.example.currencyconverter.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.data.api.ApiService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { getOkHttpClient() }
    single { getRetrofitInstance(get()) }
    single { getApiService(get()) }
    single { getSharePref(androidContext()) }
}

fun getOkHttpClient(): OkHttpClient {
    val dispatcher = Dispatcher()
    dispatcher.maxRequests = 1
    dispatcher.maxRequestsPerHost = 1

    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .addInterceptor(getHeaderInterceptor())
    okHttpClient.retryOnConnectionFailure(true)

    return okHttpClient.build()
}

private fun getHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "Token ${BuildConfig.API_KEY}")
            .build()
        val response = chain.proceed(request)
        response
    }
}

fun getRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder().serializeNulls()
        .create()
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient).build()
}

fun getApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}


fun getSharePref(context: Context): SharedPreferences {
    val pref = context.getSharedPreferences("CURRENCY_APP", MODE_PRIVATE)
    return pref
}


