package com.example.labb2.roommanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.labb2.model.Weather


@Dao
interface WeatherDao {

    @Upsert
    suspend fun upsertWeather(weather: Weather)

    @Delete
    suspend fun deleteWeather(weather: Weather)

    @Query("SELECT * FROM weather")
    fun getWeathers(): List<Weather>

    @Query("SELECT * FROM weather WHERE latitude =:latitude AND longitude =:longitude")
    fun getWeathersFromCoordinates(latitude: String, longitude: String):Weather



}