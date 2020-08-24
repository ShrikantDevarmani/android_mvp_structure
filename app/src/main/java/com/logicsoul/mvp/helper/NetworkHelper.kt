package com.logicsoul.mvp.helper

import android.content.Context
import android.net.ConnectivityManager

/**
 * Network related helper functions provider through whole application
 */
class NetworkHelper {

    /**
     * is network avaliable to device check
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting
    }

    companion object {
        private var mInstance = NetworkHelper()

        fun getNetworkHelperInstance(): NetworkHelper {
            return mInstance
        }
    }
}