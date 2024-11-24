package com.example.labb2.ui.CustomComposables

import androidx.compose.runtime.Composable
import com.example.labb2.R

fun DrawIconFromIdComposable(weatherType:String):Int {
    println("Current id = $weatherType")
    when (weatherType) {
        "1" -> return R.drawable.sun //clear skies
        "2" -> return R.drawable.cloud //partly cloudy
        "3" -> return R.drawable.cloud //cloudy
        "4" -> return R.drawable.overcast //overcast
        "5" -> return R.drawable.fog //fog
        "6" -> return R.drawable.rain //rain showers
        "7" -> return R.drawable.snow //snow showers
        "8" -> return R.drawable.thunder
        "9" -> return R.drawable.rain //rain
        "10" -> return R.drawable.snow //snow
        "11" -> return R.drawable.rain//freezing rain
        "12" -> return R.drawable.rain //drizzle
        "13" -> return R.drawable.rain//freezing drizzle
        "14" -> return R.drawable.thunder //thunder with rain
        "15" -> return R.drawable.thunder //thunder with snow
        else -> return R.drawable.sun
    }
}