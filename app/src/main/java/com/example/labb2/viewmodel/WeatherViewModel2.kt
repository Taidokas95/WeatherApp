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
import com.example.labb2.roommanager.WeatherDao
import com.example.labb2.model.interfaces.WeatherEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface WeatherViewModelInterface2 {

    val currentListOfWeathers:StateFlow<WeathersState>

    val currentTime:StateFlow<Long>

    val isUpdated:StateFlow<Boolean>

    val theRetrofitMessage:StateFlow<Pair<Boolean,String>>

    fun onEvent(event: WeatherEvent)

    fun updateTime():Boolean

    fun updateState(state:Boolean)

    fun updateRetrofitMessage(state:Boolean, message:String)

}

class WeatherViewModel2(private val dao: WeatherDao):WeatherViewModelInterface2,ViewModel() {

    private val _currentListOfWeathers = MutableStateFlow(WeathersState())
    override val currentListOfWeathers: StateFlow<WeathersState>
        get() = _currentListOfWeathers.asStateFlow()

    private val _currentTime = MutableStateFlow(System.currentTimeMillis())
    override val currentTime: StateFlow<Long>
        get() = _currentTime

    private val _isUpdated = MutableStateFlow(false)
    override val isUpdated: StateFlow<Boolean>
        get() = _isUpdated.asStateFlow()

    private val _theRetrofitMessage =  MutableStateFlow(Pair(true,"Success"))
    override val theRetrofitMessage: StateFlow<Pair<Boolean, String>>
        get() = _theRetrofitMessage.asStateFlow()


    override fun updateTime(): Boolean {
        if( System.currentTimeMillis() >= (_currentTime.value + 5000L)){
            _currentTime.value = System.currentTimeMillis()
            return true
        }
        _currentTime.value = System.currentTimeMillis()
        return false
    }

    override fun updateState(state: Boolean) {
        _isUpdated.value = state
    }

    override fun updateRetrofitMessage(state: Boolean, message:String) {
        _theRetrofitMessage.value = Pair(state,message)
    }

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
                            latitude = _currentListOfWeathers.value.latitude!!.toString(),
                            longitude = _currentListOfWeathers.value.longitude!!.toString()
                        )
                    )
                }
            }

            is WeatherEvent.LoadWeather ->{
                val backgroundJob = GlobalScope.launch(Dispatchers.Default) {
                    // Code to be executed on the background thread

                    _currentListOfWeathers.value.latitude = event.latitude
                    _currentListOfWeathers.value.longitude = event.longitude

                    println("latitude = ${event.latitude}")
                    println("longitude = ${event.longitude}")

                    //val x = dao.getWeathers()

                    val x = dao.getWeathersFromCoordinates(event.latitude.toString(), event.longitude.toString())
                    println(x)
                    val y = WeathersConverter().stringToWeather(x.weathers)

                    //TODO: Important for setting current value
                    _currentListOfWeathers.value = y

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

                when(event.commands.invoke()){
                    true -> {
                        if(updateTime()){
                            val backgroundJob = GlobalScope.launch(Dispatchers.Default){
                                val x = event.commands2.invoke(_currentListOfWeathers.value)


                                withContext(Dispatchers.Main) {
                                    if(x.first){
                                        dao.upsertWeather(
                                       Weather(
                                           weathers = WeathersConverter().weathersToString(_currentListOfWeathers.value),
                                           approvedTime = _currentListOfWeathers.value.approvedTime,
                                           latitude = _currentListOfWeathers.value.latitude!!.toString(),
                                           longitude = _currentListOfWeathers.value.longitude!!.toString()
                                       )
                                   )
                                    updateState(true)
                                    }
                                    else{
                                        updateRetrofitMessage(x.first,x.second)
                                        //_theRetrofitMessage.value = x
                                    }
                                }

                            }
                        }
                        else println("TOO Fast")
                    }
                    false ->{
                        val backgroundJob = GlobalScope.launch(Dispatchers.Default){
                            val x = dao.getWeathersFromCoordinates(_currentListOfWeathers.value.latitude!!.toString(),
                                _currentListOfWeathers.value.longitude!!.toString()
                            )
                            val y = WeathersConverter().stringToWeather(x.weathers)
                        }
                    }
                }


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