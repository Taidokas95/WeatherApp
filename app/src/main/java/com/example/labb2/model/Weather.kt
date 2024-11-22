package com.example.labb2.model

import androidx.room.Entity
import com.google.gson.Gson
import androidx.room.TypeConverter
import androidx.room.TypeConverters

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
        val gson = Gson()
        return gson.toJson(weathersState, WeathersState::class.java)
    }

	@TypeConverter
    fun stringToWeather(weathersState: String): WeathersState {
        val gson = Gson()
        return gson.fromJson(weathersState, WeathersState::class.java)
    }


}
