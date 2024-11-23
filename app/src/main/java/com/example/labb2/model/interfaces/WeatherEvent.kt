package com.example.labb2.model.interfaces

import com.example.labb2.model.WeathersState


interface WeatherEvent {

    object SaveWeather: WeatherEvent
    data class LoadWeather(val latitude:Float,val longitude: Float): WeatherEvent
    data class SetCoordinates(
        val latitude: Float,
        val longitude: Float,
        val commands: () -> Boolean,
        val commands2: (WeathersState) -> Pair<Boolean,String>
    ): WeatherEvent
    //data class DeleteWeathers(val weather: Weather): WeatherEvent

}