package com.example.currencyconverter.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService


object ConnectivityHelper {

    fun checkConnectivity(context: Context): Boolean {
        var connectivityManager: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        var connected: Boolean =
            (connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!
                .state == NetworkInfo.State.CONNECTED ||
                    connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED)
        return connected
    }

}