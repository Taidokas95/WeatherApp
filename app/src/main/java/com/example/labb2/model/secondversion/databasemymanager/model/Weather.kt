package com.example.labb2.model.secondversion.databasemymanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import androidx.room.TypeConverter
import com.example.labb2.model.secondversion.WeathersState

@Entity
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val weathers: String,
    val approvedTime:String,
    val coordinates:String
)

class WeathersConverter{

	@TypeConverter
    fun WeathersToString(weathersState: WeathersState):String{
        val gson = Gson()
        return gson.toJson(weathersState,WeathersState::class.java)
    }

	@TypeConverter
    fun StringToWeather(weathersState: String):WeathersState{
        val gson = Gson()
        return gson.fromJson(weathersState,WeathersState::class.java)
    }


}
