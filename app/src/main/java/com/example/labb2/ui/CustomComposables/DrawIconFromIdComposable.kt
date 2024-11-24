package com.example.labb2.ui.CustomComposables

import com.example.labb2.R

fun DrawIconFromIdComposable(weatherType:String):Int {
    println("Current id = $weatherType")
    when (weatherType) {
        "1.0" -> return R.drawable.l1
        "2.0" -> return R.drawable.l2
        "3.0" -> return R.drawable.l3
        "4.0" -> return R.drawable.l4
        "5.0" -> return R.drawable.l5
        "6.0" -> return R.drawable.l6
        "7.0" -> return R.drawable.l7
        "8.0" -> return R.drawable.l8
        "9.0" -> return R.drawable.l9
        "10.0" -> return R.drawable.l10
        "11.0" -> return R.drawable.l11
        "12.0" -> return R.drawable.l12
        "13.0" -> return R.drawable.l13
        "14.0" -> return R.drawable.l14
        "15.0" -> return R.drawable.l15
        "16.0" -> return R.drawable.l16
        "17.0" -> return R.drawable.l17
        "18.0" -> return R.drawable.l18
        "19.0" -> return R.drawable.l19
        "20.0" -> return R.drawable.l20
        "21.0" -> return R.drawable.l21
        "22.0" -> return R.drawable.l22
        "23.0" -> return R.drawable.l23
        "24.0" -> return R.drawable.l24
        "25.0" -> return R.drawable.l25
        "26.0" -> return R.drawable.l26
        "27.0" -> return R.drawable.l27
        else -> return R.drawable.l27
        /*"1" -> return R.drawable.sun //clear skies
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
        else -> return R.drawable.sun*/
    }
}