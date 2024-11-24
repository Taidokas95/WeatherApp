package com.example.labb2.model

// A simple weather report instance
data class WeatherState(
    val weatherDate: String,
    val weatherIcon: String,
    val temperature: Float,
)

// A weather report, which is comprised of a collection of weather states
data class WeathersState(
    val weathers: MutableList<WeatherState> = mutableListOf(),
    var approvedTime: String = "",
    var latitude: Float? = null,
    var longitude: Float? = null
)