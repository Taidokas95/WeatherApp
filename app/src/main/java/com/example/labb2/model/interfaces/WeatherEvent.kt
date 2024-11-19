package com.example.labb2.model.interfaces


interface WeatherEvent {

    object SaveWeather: WeatherEvent
    data class LoadWeather(val latitude:Float,val longitude: Float): WeatherEvent
    data class SetCoordinates(val latitude:Float, val longitude:Float): WeatherEvent
    //data class setCoordinates(val coordinates:String): WeatherEvent
    //data class DeleteWeathers(val weather: Weather): WeatherEvent

}