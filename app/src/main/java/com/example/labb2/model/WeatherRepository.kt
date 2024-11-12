package com.example.labb2.model

//example code
/*class WeatherRepository(private val weatherDao: WeatherDao, private val apiService: WeatherService) {
     val allForecasts: Flow<List<WeatherForecast>> = weatherDao.getAllForecasts()

    // Function to fetch weather data from the API and save it locally
    suspend fun fetchWeatherForecast(lon: Double, lat: Double) {
        try {
            // Fetch from the remote service
            val response = weatherService.getWeatherForecast(lon, lat)
            if (response.isSuccessful) {
                val forecastData = response.body()?.toWeatherForecastList() // Convert response to list of entities

                if (forecastData != null) {
                    // Clear old data and insert new data into Room
                    weatherDao.deleteAllForecasts()
                    weatherDao.insertAllForecasts(forecastData)
                }
            } else {
                // Handle API error
                throw Exception("API Error: ${response.message()}")
            }
        } catch (e: Exception) {
            // Handle network or conversion errors
            throw e
        }
    }

    fun WeatherResponse.toWeatherForecastList(): List<WeatherForecast> {
    return this.timeSeries.map { timeSeriesEntry ->
        WeatherForecast(
            date = timeSeriesEntry.validTime,
            temperature = timeSeriesEntry.getTemperature(), // Helper method to extract temperature
            cloudCoverage = timeSeriesEntry.getCloudCoverage(), // Helper method to extract cloud coverage
            humidity = timeSeriesEntry.getHumidity() // Helper method to extract humidity
        )
    }
}
}*/