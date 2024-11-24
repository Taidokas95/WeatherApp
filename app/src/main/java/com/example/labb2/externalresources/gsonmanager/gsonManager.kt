package com.example.labb2.externalresources.gsonmanager

import com.example.labb2.model.WeathersState
import com.google.gson.Gson

class gsonManager private constructor() {

    fun toJson(weathersState: WeathersState):String{

        return gson!!.toJson(weathersState, WeathersState::class.java)
    }

    fun fromJson(weathersState: String): WeathersState {
        return gson!!.fromJson(weathersState, WeathersState::class.java)
    }

    companion object{
        private var gsonManager:gsonManager? = null
        private var gson:Gson? = null

        fun getGsonManager():gsonManager{
            if(gsonManager==null){
                gsonManager = gsonManager()
                gson = Gson()
            }
            return gsonManager as gsonManager
        }
    }

}