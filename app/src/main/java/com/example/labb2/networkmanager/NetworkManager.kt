package com.example.labb2.networkmanager

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.labb2.CustomExceptions.OutOfBoundsException

class NetworkManager {


    companion object{
        private var retrofitImp:RetrofitImp? = null
        private var networkManager:NetworkManager? = null

        fun createNetworkManager():NetworkManager{
            if(networkManager == null) {
                networkManager = NetworkManager()
                retrofitImp = RetrofitImp.createRetroFitImp()
            }
            return networkManager as NetworkManager
        }


    }

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