package com.example.labb2.model

import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.labb2.externalresources.gsonmanager.GsonManager


/**
 *
 * Defines a entity class which is used to work with the weather database
 *
 */
@Entity(primaryKeys = ["latitude", "longitude"])
data class Weather(
    @TypeConverters(WeathersConverter::class) val weathers: String,
    val approvedTime:String,
    val latitude:String,
    val longitude:String
)


/**
 *
 * A class which represents different converters used for the data base to convert between different data types when storing data
 *
 */
class WeathersConverter{

	@TypeConverter
    fun weathersToString(weathersState: WeathersState):String{
        return GsonManager.getGsonManager().toJson(weathersState)
    }

	@TypeConverter
    fun stringToWeather(weathersState: String): WeathersState {
        return GsonManager.getGsonManager().fromJson(weathersState)
    }


}
