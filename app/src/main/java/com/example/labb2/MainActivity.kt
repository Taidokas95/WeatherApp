package com.example.labb2

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.labb2.model.secondversion.databasemymanager.model.WeatherDatabase
import com.example.labb2.ui.screens.secondversion.WeatherScreen
import com.example.labb2.ui.theme.LabB2Theme
import com.example.labb2.viewmodel.secondversion.WeatherViewModel

class MainActivity : ComponentActivity() {


    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "weathers.db"
        )
            .build()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabB2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //WeatherScreen(Modifier.padding(innerPadding))

                    val config = resources.configuration

                    if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        GreetingLandscape(Modifier.padding(innerPadding))
                    }
                    else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        GreetingPortrait(Modifier.padding(innerPadding))
                    }

                    else{
                        Greeting("Android")
                    }

                    // Instantiate the viewmodel
                    //val weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory(db.dao))

                    //WeatherScreen(vm = weatherViewModel,onEvent = weatherViewModel::onEvent)

                //Greeting(
                    //    name = "Android",
                    //    modifier = Modifier.padding(innerPadding)
                    //)
                }
            }
        }
    }
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabB2Theme {
        Greeting("Android")
    }
}