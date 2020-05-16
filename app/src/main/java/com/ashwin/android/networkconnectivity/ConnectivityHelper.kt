package com.ashwin.android.networkconnectivity

import android.content.Context
import android.net.*
import android.os.Build
import android.util.Log

object ConnectivityHelper {
    const val OFFLINE: Int = 0
    const val ONLINE_WIFI: Int = 1
    const val ONLINE_DATA: Int = 2

    @Volatile var status: Int = -1

    private var cm: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    fun register(context: Context) {
        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val builder: NetworkRequest.Builder = NetworkRequest.Builder()

            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Log.i("network-status", "onAvailable!")
                    val isWifi: Boolean = cm?.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
                    status = if (isWifi) {
                        ONLINE_WIFI
                    } else {
                        ONLINE_DATA
                    }
                }

                override fun onLost(network: Network) {
                    Log.i("network-status", "onLost!")
                    status = OFFLINE
                }
            }

            cm?.registerNetworkCallback(builder.build(), networkCallback!!)
            Log.d("network-status", "Registered")
        }
    }

    fun unregister() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cm?.unregisterNetworkCallback(networkCallback)
            Log.d("network-status", "Unregistered")
        }
    }

    fun isOnline(): Boolean {
        return status > 0
    }

    fun hasWifi(): Boolean {
        return status == ONLINE_WIFI
    }
}
