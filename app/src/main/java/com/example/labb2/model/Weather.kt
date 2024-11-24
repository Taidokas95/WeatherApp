package com.example.labb2.model

import androidx.room.Entity
import com.google.gson.Gson
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.labb2.externalresources.gsonmanager.gsonManager

@Entity(primaryKeys = ["latitude", "longitude"])
data class Weather(
    @TypeConverters(WeathersConverter::class) val weathers: String,
    val approvedTime:String,
    val latitude:String,
    val longitude:String
)

class WeathersConverter{

	@TypeConverter
    fun weathersToString(weathersState: WeathersState):String{
        //val gson = gsonManager.getGsonManager()
        //val gson = Gson()
        //val x = WeathersState::class.java
        //return gson.toJson(weathersState, WeathersState::class.java)
        return gsonManager.getGsonManager().toJson(weathersState)
    }

	@TypeConverter
    fun stringToWeather(weathersState: String): WeathersState {
        //val gson = Gson()
        //return gson.fromJson(weathersState, WeathersState::class.java)
        return gsonManager.getGsonManager().fromJson(weathersState)
    }


}
