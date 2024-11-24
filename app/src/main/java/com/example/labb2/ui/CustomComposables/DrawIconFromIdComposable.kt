package com.example.labb2.ui.CustomComposables

import com.example.labb2.R

/**
 *
 * A class which returns a painter resource id based on the weatherType icon id.
 *
 * @param weatherType, The weatherType icon used to return a icon id
 *
 * @return Returns a drawable id which represents the weatherType icon id.
 *
 */
fun DrawIconFromIdComposable(weatherType: String): Int {
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
    }
}