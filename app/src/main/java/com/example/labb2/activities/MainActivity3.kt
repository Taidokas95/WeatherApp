package com.example.labb2.activities


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

// Persistant

import androidx.room.Room
import com.example.labb2.model.WeathersState
import com.example.labb2.externalresources.networkmanager.NetworkManager
import com.example.labb2.externalresources.networkmanager.RunnableService
import com.example.labb2.externalresources.roommanager.WeatherDatabase
import com.example.labb2.ui.screens.MainScreen
import com.example.labb2.ui.theme.LabB2Theme
import com.example.labb2.viewmodel.WeatherViewModel2


/**
 *
 * An activity which represents the weather report application
 *
 */
class MainActivity3 : ComponentActivity() {

    // Defines a weather database
    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "weathers.db"
        )
            .build()
    }

    // A network manager instance
    private val networkManager = NetworkManager.createNetworkManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabB2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Instantiate the viewmodel with a specific database dao
                    val weatherViewModel: WeatherViewModel2 = viewModel(
                        factory = WeatherViewModel2.Factory(db.dao)
                    )

                    val x = {networkManager.isOnline(application)}  // Command for representing a Network manager function

                    // A command for representing a runnable network service
                    val y = {
                        weathersState: WeathersState
                        -> networkManager
                            .runNetworkService(
                                RunnableService.RetrofitRunner(weathersState)
                            )
                    }

                    var z = listOf<Any>(x,y)

                    // Instantiate the MainScreen with a viewmodel, an onEvent interface and a two commands
                    //HomeScreen(vm = gameViewModel,MyTTS(this))
                    MainScreen(
                        vm = weatherViewModel,
                        onEvent = weatherViewModel::onEvent,
                        commands = z[0] as () -> Boolean, commands2 = z[1] as (WeathersState) -> Pair<Boolean,String>
                    )

                }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
        text = "Hello $name!",
        modifier = modifier
    )

}

@Composable
fun GreetingPortrait(modifier: Modifier = Modifier) {

    Text(
        text = "Is Portrait",
        modifier = modifier
    )

}

@Composable
fun GreetingLandscape(modifier: Modifier = Modifier) {

    Text(
        text = "Is Landscape",
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    //TestLab1_2Theme {
    //    Greeting("Android")
    //}
}