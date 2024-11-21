package com.example.labb2.viewmodel

//import com.example.testlab1_2.myexternalresource.databasemymanager.interfaces.WeatherDao
import android.app.Application
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
import com.example.labb2.roommanager.WeatherDao
import com.example.labb2.model.interfaces.WeatherEvent
import com.example.labb2.networkmanager.NetworkManager
import com.example.labb2.networkmanager.RunnableService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface WeatherViewModelInterface2 {

    val currentListOfWeathers:StateFlow<WeathersState>

    fun onEvent(event: WeatherEvent)
}

class WeatherViewModel2(private val dao: WeatherDao):WeatherViewModelInterface2,ViewModel() {

    private val _currentListOfWeathers = MutableStateFlow(WeathersState())
    override val currentListOfWeathers: StateFlow<WeathersState>
        get() = _currentListOfWeathers.asStateFlow()

   // private val networkManager = NetworkManager.createNetworkManager()


    companion object {
        fun Factory(dao: WeatherDao):ViewModelProvider.Factory{
            return viewModelFactory {
                initializer {
                    WeatherViewModel2(dao)
                }
            }
        }

    }


    override fun onEvent(event: WeatherEvent){
        when(event){
            is WeatherEvent.SaveWeather ->{

                //val (latitude, longitude)= Pair(55.3f,33.4f)
                //val approvedTime = "2024-06-04 20.00"

                /*_currentListOfWeathers.value = WeathersState(
                    weathers = mutableListOf(WeatherState("2024-06-04 0100","w31",55.3f)),
                    approvedTime = _currentListOfWeathers.value.approvedTime,
                    latitude = _currentListOfWeathers.value.latitude!!,
                    longitude = _currentListOfWeathers.value.longitude!!
                )*/

                viewModelScope.launch {

                    dao.upsertWeather(
                        Weather(
                            weathers = WeathersConverter().weathersToString(_currentListOfWeathers.value),
                            approvedTime = _currentListOfWeathers.value.approvedTime,
                            latitude = _currentListOfWeathers.value.latitude!!,
                            longitude = _currentListOfWeathers.value.longitude!!
                        )
                    )
                }
            }

            is WeatherEvent.LoadWeather ->{
                val backgroundJob = GlobalScope.launch(Dispatchers.Default) {
                    // Code to be executed on the background thread

                    //val x = dao.getWeathers()
                    val x = dao.getWeathersFromCoordinates(_currentListOfWeathers.value.latitude!!, _currentListOfWeathers.value.longitude!!)
                    val y = WeathersConverter().stringToWeather(x.weathers)

                    // When your task is complete and you want to update the UI or perform other tasks on the main thread, use Dispatchers.Main
                    withContext(Dispatchers.Main) {
                        Log.e("access dao", "${x}")
                        //println(x[0].weathers)
                        println("Coordinates = [${y.latitude};${y.longitude}]")
                        println("Approved time = ${y.approvedTime}")
                        for(w in y.weathers){
                            println("Temperature = ${w.temperature}, Symbol = ${w.weatherIcon}, date = ${w.weatherDate}")
                        }
                    }
                }
            }

            is WeatherEvent.SetCoordinates ->{
                _currentListOfWeathers.value.latitude = event.latitude
                _currentListOfWeathers.value.longitude = event.longitude
                //getWeathersFromDatabase(_currentListOfWeathers.value)

                when(event.commands.invoke()){
                    true -> {event.commands2.invoke(_currentListOfWeathers.value)}
                    false ->{
                        val backgroundJob = GlobalScope.launch(Dispatchers.Default){
                            val x = dao.getWeathersFromCoordinates(_currentListOfWeathers.value.latitude!!, _currentListOfWeathers.value.longitude!!)
                            val y = WeathersConverter().stringToWeather(x.weathers)
                        }
                    }
                }

                    //networkManager.runNetworkService(RunnableService.RetrofitRunner(_currentListOfWeathers.value))


                /*val backgroundJob = GlobalScope.launch(Dispatchers.Default) {
                    //val latitude =  event.latitude
                    //val longitude =  event.longitude

                    println(localWeathersState.latitude)
                    println(localWeathersState.longitude)

                    //netDownloadTest()

                    // DO internet stuff

                    // When your task is complete and you want to update the UI or perform other tasks on the main thread, use Dispatchers.Main
                    withContext(Dispatchers.Main) {

                        //val approvedTime = "2024-06-04 20.00"

                        //val newWeathers = mutableListOf(WeatherState("2024-06-04 0100","w31",55.3f))


                        _currentListOfWeathers.value = localWeathersState
                            //WeathersState(
                        //weathers = newWeathers,
                            //approvedTime = approvedTime,
                            //latitude = latitude,
                            //longitude = longitude
                        //)
                    }

                }*/
            }
        }
    }

}