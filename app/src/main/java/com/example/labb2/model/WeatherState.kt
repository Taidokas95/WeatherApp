package com.example.labb2.model

data class WeatherState(
    val weatherDate:String,
    val weatherIcon:String,
    val temperature:Float,
)

data class WeathersState(
    val weathers:MutableList<WeatherState> = mutableListOf(),
    var approvedTime:String = "",
    var latitude:Float? = null,
    var longitude:Float? = null
)