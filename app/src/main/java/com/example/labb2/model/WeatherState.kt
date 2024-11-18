package com.example.labb2.model

data class WeatherState(
    val weatherDate:String,
    val weatherIcon:String,
    val temperature:Float,
)

data class WeathersState(
    val weathers:List<WeatherState> = emptyList(),
    val approvedTime:String = "",
    val coordinates:String = ""
)