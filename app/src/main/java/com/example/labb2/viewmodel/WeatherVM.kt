package com.example.labb2.viewmodel

//example code
/*class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

   val allForecasts: LiveData<List<WeatherForecast>> = repository.allForecasts.asLiveData()

    val errorLiveData = MutableLiveData<String>()

    // Function to fetch fresh data from the API
    fun refreshWeatherForecast(lon: Double, lat: Double) {
        viewModelScope.launch {
            try {
                repository.fetchWeatherForecast(lon, lat)
            } catch (e: Exception) {
                errorLiveData.postValue("Error fetching data: ${e.message}")
            }
        }
    }
}*/