package com.muasdev.simpleandroidmvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkConnectivity @Inject constructor(
    private val context: Context
) {

    fun isInternetOn(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return internetCheck(connectivityManager)
    }

    private fun internetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }
}