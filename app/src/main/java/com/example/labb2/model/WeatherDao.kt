package com.example.labb2.model

//example code
/*@Dao
interface WeatherDao {

    // Insert a single forecast
    @Insert
    suspend fun insertForecast(forecast: WeatherForecast)

    // Retrieve all weather forecasts, ordered by date
    @Query("SELECT * FROM weather_forecast ORDER BY date ASC")
    fun getAllForecasts(): Flow<List<WeatherForecast>>

    // Delete all forecasts (for refreshing data, if needed)
    @Query("DELETE FROM weather_forecast")
    suspend fun deleteAllForecasts()
}*/