package com.example.labb2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.labb2.model.WeatherState
import com.example.labb2.model.WeathersState
import com.example.labb2.model.interfaces.WeatherDao
import com.example.labb2.model.interfaces.WeatherEvent
import com.example.labb2.model.Weather
import com.example.labb2.model.WeathersConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface WeatherViewModelInterface {

    val latitude: StateFlow<Float>
    val longitude: StateFlow<Float>
    val currentListOfWeathers: StateFlow<WeathersState>

    fun onEvent(event: WeatherEvent)
}

class WeatherViewModel(private val dao: WeatherDao) : WeatherViewModelInterface, ViewModel() {

    private val _latitude = MutableStateFlow(-1f)
    override val latitude: StateFlow<Float>
        get() = _latitude
    private val _longitude = MutableStateFlow(-1f)
    override val longitude: StateFlow<Float>
        get() = _longitude
    private val _current_ListOfWeathers = MutableStateFlow(WeathersState())
    override val currentListOfWeathers: StateFlow<WeathersState>
        get() = _current_ListOfWeathers.asStateFlow()

    companion object {
        fun Factory(dao: WeatherDao): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    WeatherViewModel(dao)
                }
            }
        }
    }


    override fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.SaveWeather -> {

                val coordinates = "x = 1, y = 2"
                val approvedTime = "2024-06-04 20.00"

                /*val weathersState = WeathersState(
                    weathers = listOf(WeatherState("2024-06-04 0100", "w31", 55.3f)),
                    approvedTime = approvedTime,
                    coordinates = coordinates
                )

                viewModelScope.launch {
                    dao.upsertWeather(
                        Weather(
                            weathers = WeathersConverter().WeathersToString(weathersState),
                            approvedTime = weathersState.approvedTime,
                            coordinates = weathersState.coordinates
                        )
                    )
                }*/
            }

            is WeatherEvent.LoadWeather -> {

                viewModelScope.launch {
                    println("Hello")
                    println(dao.getWeathers())
                }
            }
        }
    }
}

class FakeVM : WeatherViewModelInterface {
    private val _latitude = MutableStateFlow(-1f)
    override val latitude: StateFlow<Float>
        get() = _latitude
    private val _longitude = MutableStateFlow(-1f)
    override val longitude: StateFlow<Float>
        get() = _longitude
    private val _current_ListOfWeathers = MutableStateFlow(WeathersState())
    override val currentListOfWeathers: StateFlow<WeathersState>
        get() = _current_ListOfWeathers.asStateFlow()

    override fun onEvent(event: WeatherEvent) {
        TODO("Not yet implemented")
    }
}