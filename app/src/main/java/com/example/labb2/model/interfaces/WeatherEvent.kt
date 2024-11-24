package com.example.labb2.model.interfaces

import com.example.labb2.model.WeathersState


/**
 *
 * An interface which represents different commands which can be used to perform a specific task
 *
 */
interface WeatherEvent {

    object SaveWeather: WeatherEvent
    data class LoadWeather(val latitude:Float,val longitude: Float): WeatherEvent
    data class SetCoordinates(
        val latitude: Float,
        val longitude: Float,
        val commands: () -> Boolean,
        val commands2: (WeathersState) -> Pair<Boolean,String>
    ): WeatherEvent

}