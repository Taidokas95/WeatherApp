package com.example.labb2.model.secondversion.databasemymanager.interfaces


interface WeatherEvent {

    object SaveWeather: WeatherEvent
    object LoadWeather: WeatherEvent
    data class setCoordinates(val coordinates:String): WeatherEvent
    //data class DeleteWeathers(val weather: Weather): WeatherEvent

}