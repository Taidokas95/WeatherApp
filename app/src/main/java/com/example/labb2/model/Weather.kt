package com.example.labb2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @TypeConverters(WeathersConverter::class) val weathers: String,
    val approvedTime:String,
    val latitude:Float,
    val longitude:Float
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
