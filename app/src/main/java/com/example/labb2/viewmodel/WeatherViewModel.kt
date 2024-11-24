package com.example.labb2.viewmodel

//import com.example.testlab1_2.myexternalresource.databasemymanager.interfaces.WeatherDao
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.labb2.model.Weather
import com.example.labb2.model.WeathersConverter
import com.example.labb2.model.WeathersState
//import com.example.labb2.networkmanager.getWeathersFromDatabase
import com.example.labb2.externalresources.roommanager.WeatherDao
import com.example.labb2.model.interfaces.WeatherEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * Interface class for representing the view model
 *
 */
interface WeatherViewModelInterface2 {

    val currentListOfWeathers: StateFlow<WeathersState>
    val currentTime: StateFlow<Long>
    val isUpdated: StateFlow<Boolean>
    val theRetrofitMessage: StateFlow<Pair<Boolean, String>>

    fun onEvent(event: WeatherEvent)
    fun updateTime(): Boolean
    fun updateState(state: Boolean)
    fun updateRetrofitMessage(state: Boolean, message: String)
}

/**
 *
 * The weather view model
 *
 * @param dao: The data access object used for working with the room database
 *
 */
class WeatherViewModel2(private val dao: WeatherDao) : WeatherViewModelInterface2, ViewModel() {

    private val _currentListOfWeathers =
        MutableStateFlow(WeathersState())  // The list of weather reports to present
    override val currentListOfWeathers: StateFlow<WeathersState>
        get() = _currentListOfWeathers.asStateFlow()

    private val _currentTime =
        MutableStateFlow(System.currentTimeMillis())     // The current time to prevent multiple calls in a short time
    override val currentTime: StateFlow<Long>
        get() = _currentTime

    private val _isUpdated =
        MutableStateFlow(true)                      // Variable for updating composable list
    override val isUpdated: StateFlow<Boolean>
        get() = _isUpdated.asStateFlow()

    private val _theRetrofitMessage = MutableStateFlow(
        Pair(
            true,
            "Success"
        )
    )       // Variable for representing different messages to present
    override val theRetrofitMessage: StateFlow<Pair<Boolean, String>>
        get() = _theRetrofitMessage.asStateFlow()

    /**
     *
     * Update time in order to prevent multiple calls in a short time
     *
     * @return Returns true if 5 seconds have passed
     *
     */
    override fun updateTime(): Boolean {
        if (System.currentTimeMillis() >= (_currentTime.value + 5000L)) {
            _currentTime.value = System.currentTimeMillis()
            return true
        }
        _currentTime.value = System.currentTimeMillis()
        return false
    }

    /**
     *
     * Update a state used for keeping track of presenting the current weather report
     *
     * @param state, The state to set with
     *
     */
    override fun updateState(state: Boolean) {
        _isUpdated.value = state
    }

    /**
     *
     * Update the message variable with a different state and message
     *
     * @param state, The state to update with
     *
     * @param message, The message to update with
     *
     */
    override fun updateRetrofitMessage(state: Boolean, message: String) {
        _theRetrofitMessage.value = Pair(state, message)
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
                    WeatherViewModel2(dao)
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
            // Save weather event
            is WeatherEvent.SaveWeather -> {
                viewModelScope.launch {

                    dao.upsertWeather(
                        Weather(
                            weathers = WeathersConverter().weathersToString(_currentListOfWeathers.value),
                            approvedTime = _currentListOfWeathers.value.approvedTime,
                            latitude = _currentListOfWeathers.value.latitude!!.toString(),
                            longitude = _currentListOfWeathers.value.longitude!!.toString()
                        )
                    )
                }
            }

            // Load weather event
            is WeatherEvent.LoadWeather -> {
                val backgroundJob = GlobalScope.launch(Dispatchers.Default) {
                    // Code to be executed on the background thread
                    _currentListOfWeathers.value.latitude = event.latitude
                    _currentListOfWeathers.value.longitude = event.longitude

                    println("latitude = ${event.latitude}")
                    println("longitude = ${event.longitude}")

                    val x = dao.getWeathersFromCoordinates(
                        event.latitude.toString(),
                        event.longitude.toString()
                    )
                    //println(x)
                    val y = WeathersConverter().stringToWeather(x.weathers)

                    //TODO: Important for setting current value
                    _currentListOfWeathers.value = y

                    // When your task is complete and you want to update the UI or perform other tasks on the main thread, use Dispatchers.Main
                    withContext(Dispatchers.Main) {
                        Log.e("access dao", "${x}")
                        //println(x[0].weathers)
                        println("Coordinates = [${y.latitude};${y.longitude}]")
                        println("Approved time = ${y.approvedTime}")
                        for (w in y.weathers) {
                            println("Temperature = ${w.temperature}, Symbol = ${w.weatherIcon}, date = ${w.weatherDate}")
                        }
                    }
                }
            }

            // Set new coordinates
            is WeatherEvent.SetCoordinates -> {
                _currentListOfWeathers.value.latitude = event.latitude
                _currentListOfWeathers.value.longitude = event.longitude

                when (event.commands.invoke()) {
                    true -> {
                        // If 5 seconds have passed
                        if (updateTime()) {

                            // Run task for updating the list of weather reports
                            val backgroundJob = GlobalScope.launch(Dispatchers.Default) {
                                val x = event.commands2.invoke(_currentListOfWeathers.value)
                                withContext(Dispatchers.Main) {
                                    // Either insert or update the database if a successful network service has occurred
                                    if (x.first) {
                                        dao.upsertWeather(
                                            Weather(
                                                weathers = WeathersConverter().weathersToString(
                                                    _currentListOfWeathers.value
                                                ),
                                                approvedTime = _currentListOfWeathers.value.approvedTime,
                                                latitude = _currentListOfWeathers.value.latitude!!.toString(),
                                                longitude = _currentListOfWeathers.value.longitude!!.toString()
                                            )
                                        )
                                        updateState(true)
                                    }
                                    // Set the message variable with the yielded state and message
                                    else {
                                        updateRetrofitMessage(x.first, x.second)
                                    }
                                }
                            }
                        }
                        // Set too fast if user has pressed too quickly
                        else {
                            updateRetrofitMessage(false, "Too fast")
                        }
                    }
                    // Either load stored weather list or present a message where there is not weather report of such specified coordinates
                    false -> {
                        val backgroundJob = GlobalScope.launch(Dispatchers.Default) {
                            updateRetrofitMessage(false, "No internet")
                            try {
                                val x = dao.getWeathersFromCoordinates(
                                    _currentListOfWeathers.value.latitude!!.toString(),
                                    _currentListOfWeathers.value.longitude!!.toString()
                                )
                                val y = WeathersConverter().stringToWeather(x.weathers)
                                _currentListOfWeathers.value = y
                            } catch (e: NullPointerException) {
                                updateRetrofitMessage(false, "No content")
                            }
                        }
                    }
                }
            }
        }
    }
}