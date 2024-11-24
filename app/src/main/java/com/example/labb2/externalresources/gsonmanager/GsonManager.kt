package com.example.labb2.externalresources.gsonmanager

import com.example.labb2.model.WeathersState
import com.google.gson.Gson

/**
 * A class which parses json data with Gson.
 */
class GsonManager private constructor() {

    /**
     * A class which converts a weathers state class to a string
     *
     * @param weathersState, the weather state to process
     *
     * @return Returns a string representation of the weather state class
     */
    fun toJson(weathersState: WeathersState):String{
        return gson!!.toJson(weathersState, WeathersState::class.java)
    }


    /**
     *
     * A class which converts a string representation of a weathers state to its original class format
     *
     * @param weathersState, the string representation to parse
     *
     * @return Returns a weathers state class which represents the parsed string data
     *
     */
    fun fromJson(weathersState: String): WeathersState {
        return gson!!.fromJson(weathersState, WeathersState::class.java)
    }

    companion object{
        private var gsonManager:GsonManager? = null
        private var gson:Gson? = null

        /**
         * Get an instance of the gson manager as a singleton
         *
         * @return Returns a gson manager instance
         */
        fun getGsonManager():GsonManager{
            if(gsonManager==null){
                gsonManager = GsonManager()
                gson = Gson()
            }
            return gsonManager as GsonManager
        }
    }

}