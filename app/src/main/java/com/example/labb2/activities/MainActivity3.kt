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
import com.example.labb2.networkmanager.NetworkManager
import com.example.labb2.networkmanager.RunnableService
import com.example.labb2.roommanager.WeatherDao
import com.example.labb2.roommanager.WeatherDatabase
import com.example.labb2.ui.MainScreen
import com.example.labb2.ui.theme.LabB2Theme
import com.example.labb2.viewmodel.WeatherViewModel2

class MainActivity3 : ComponentActivity() {

    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            //ContactDatabase::class.java,
            WeatherDatabase::class.java,
            "weathers.db"
            //"contacts.db"
        )
            //.addTypeConverter(WeathersConverter())
            .build()
    }

    private val networkManager = NetworkManager.createNetworkManager()

    /*private val viewModel by viewModels<WeatherViewModel>(//viewModels<ContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>):T{
                    return WeatherViewModel(db.dao) as T
                }
            }
        }
    )*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabB2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Instantiate the viewmodel
                    val weatherViewModel: WeatherViewModel2 = viewModel(
                        factory = WeatherViewModel2.Factory(db.dao)//GameVM.Factory
                    )

                    val x = {networkManager.isOnline(application)}
                    val y = {
                        weathersState: WeathersState
                        -> networkManager
                            .runNetworkService(
                                RunnableService.RetrofitRunner(weathersState)
                            )
                    }

                    var z = listOf<Any>(x,y)

                    // Instantiate the homescreen with a text to speach class (MyTTS) with the ComponentActivity Context
                    //HomeScreen(vm = gameViewModel,MyTTS(this))
                    MainScreen(
                        vm = weatherViewModel,
                        onEvent = weatherViewModel::onEvent,
                        commands = z[0] as () -> Boolean, commands2 = z[1] as (WeathersState) -> Pair<Boolean,String>
                    )

                    /*val config = resources.configuration

                    if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        GreetingLandscape()
                    }
                    else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        GreetingPortrait()
                    }

                    else{
                        Greeting("Android")
                    }*/




                    //val test = Test(application = application)

                    //test.threadTest()
                    //test.netDownloadTest()
                    //                  test.JSONparsingTest()
                    //test.Persitant()
                    //test.isNetworkAvailable()
                    //ContactScreen(state = state, onEvent = viewModel::onEvent)


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