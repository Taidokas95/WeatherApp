package com.example.labb2.externalresources.roommanager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.labb2.model.Weather
import com.example.labb2.model.WeathersConverter

/**
 *
 * A class which represents a data base, which is implemented with Room.
 *
 */
@Database(
    entities = [Weather::class],
    version = 1
)
@TypeConverters(WeathersConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val dao: WeatherDao
}