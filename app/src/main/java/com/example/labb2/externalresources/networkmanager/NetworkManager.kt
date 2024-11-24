package com.example.labb2.externalresources.networkmanager

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.labb2.exceptions.OutOfBoundsException


/**
 *
 * A class which handles network related tasks.
 *
 */
class NetworkManager {


    companion object{
        private var retrofitImp: RetrofitImp? = null
        private var networkManager: NetworkManager? = null


        /**
         * Get a network manager class as a singleton
         *
         * @return Returns an instance of the network manager class
         */
        fun createNetworkManager(): NetworkManager {
            if(networkManager == null) {
                networkManager = NetworkManager()
                retrofitImp = RetrofitImp.createRetroFitImp()
            }
            return networkManager as NetworkManager
        }


    }

    /**
     *
     * Checks if the application is connected to the internet
     *
     * @param application, the application context used for accessing the connectivity manager class
     *
     * @return Returns true if app is connected to the internet
     *
     */
    fun isOnline(application: Application):Boolean{
            val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val network = connectivityManager?.activeNetwork
            val capabilities = connectivityManager?.getNetworkCapabilities(network)
            if(capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))){
                println("network")
                return true
            }
            else{
                println("no network")
                return false
            }
    }

    /**
     *
     * Runs a network service
     *
     * @param value, the runnable service which represents what type of network service is to be performed.
     *
     * @return Returns a Pair class which represents if a network service has been successfully performed.
     *
     */
    fun runNetworkService(value: RunnableService.RetrofitRunner): Pair<Boolean, String?> {
        try {
            retrofitImp!!.runService(value)
            return Pair(true,"Success")
        }catch(e: OutOfBoundsException){
            println("Hello ${e.message}")
           return Pair(false,e.message)
        }
    }

}