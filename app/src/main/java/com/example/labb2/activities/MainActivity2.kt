package com.example.labb2.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.labb2.roommanager.WeatherDatabase
import com.example.labb2.ui.MainScreen
import com.example.labb2.ui.theme.LabB2Theme
import com.example.labb2.viewmodel.WeatherViewModel

class MainActivity2 : ComponentActivity() {

    private val db by lazy {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabB2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val weatherViewModel: WeatherViewModel = viewModel(
                        factory = WeatherViewModel.Factory(db.dao)//GameVM.Factory
                    )
                    //MainScreen(onEvent = weatherViewModel::onEvent,vm = weatherViewModel)

                }
            }
        }
    }
}