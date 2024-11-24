package com.example.labb2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.labb2.model.WeathersState
import com.example.labb2.externalresources.roommanager.WeatherDao
import com.example.labb2.model.interfaces.WeatherEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 *
 * Interface class for representing the view model
 *
 */
interface WeatherViewModelInterface {

    val latitude: StateFlow<Float>
    val longitude: StateFlow<Float>
    val currentListOfWeathers: StateFlow<WeathersState>

    fun setLongitude(float: Float)
    fun setLatitude(float: Float)
    fun onEvent(event: WeatherEvent)
}


/**
 *
 * The weather view model
 *
 * @param dao: The data access object used for working with the room database
 *
 */
class WeatherViewModel(private val dao: WeatherDao) : WeatherViewModelInterface, ViewModel() {

    private val _latitude = MutableStateFlow(-1f)
    override val latitude: StateFlow<Float>
        get() = _latitude
    private val _longitude = MutableStateFlow(-1f)
    override val longitude: StateFlow<Float>
        get() = _longitude
    private val _currentListOfWeathers = MutableStateFlow(WeathersState())
    override val currentListOfWeathers: StateFlow<WeathersState>
        get() = _currentListOfWeathers

    /**
     *
     * Set the longitude
     *
     * @param float, The longitude value
     *
     * */
    override fun setLongitude(float: Float) {
        _longitude.value = float
    }

    /**
     *
     * Set the latitude
     *
     * @param float, The latitude value
     *
     */
    override fun setLatitude(float: Float) {
        _latitude.value = float
    }


    companion object {

        /**
         *
         * Create the view model with a dao
         *
         * @param dao, the data access object used for creating the view model
         *
         * @return Returns a created viewmodel
         *
         */
        fun Factory(dao: WeatherDao): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    WeatherViewModel(dao)
                }
            }
        }
    }


    /**
     *
     * A class for representing different weather event commands
     *
     * @param event, The weather event used
     *
     */
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

    override fun setLongitude(float: Float) {
        TODO("Not yet implemented")
    }

    override fun setLatitude(float: Float) {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: WeatherEvent) {
        TODO("Not yet implemented")
    }
}