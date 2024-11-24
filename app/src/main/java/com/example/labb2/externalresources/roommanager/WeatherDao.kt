package com.example.labb2.externalresources.roommanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.labb2.model.Weather

/**
 *
 * A Database access object (DAO) interface, which is used as a database for weather data
 *
 */

@Dao
interface WeatherDao {

    /**
     *
     * Update or Insert weather data
     *
     * @param weather, the weather data to either update or insert
     *
     */
    @Upsert
    suspend fun upsertWeather(weather: Weather)

    /**
     *
     * Delete weather data
     *
     * @param weather, the weather data to delete
     *
     */
    @Delete
    suspend fun deleteWeather(weather: Weather)

    /**
     *
     * Get all weather instances
     *
     * @return Returns all stored weather instances
     *
     */
    @Query("SELECT * FROM weather")
    fun getWeathers(): List<Weather>

    /**
     *
     * Get specific weather data based on specific coordinates
     *
     * @return Returns a specific weather instance
     *
     */
    @Query("SELECT * FROM weather WHERE latitude =:latitude AND longitude =:longitude")
    fun getWeathersFromCoordinates(latitude: String, longitude: String): Weather


}