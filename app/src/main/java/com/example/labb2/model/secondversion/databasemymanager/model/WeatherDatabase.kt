package com.example.labb2.model.secondversion.databasemymanager.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.labb2.model.secondversion.databasemymanager.interfaces.WeatherDao
import com.example.labb2.model.secondversion.databasemymanager.model.Weather
import com.example.labb2.model.secondversion.databasemymanager.model.WeathersConverter

//import com.example.labb2.model.secondversion.databasemymanager.interfaces.WeatherDao

@Database(
    entities = [Weather::class],
    version = 1
)

@TypeConverters(WeathersConverter::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract  val dao: WeatherDao
}