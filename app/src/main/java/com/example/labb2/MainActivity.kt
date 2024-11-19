package com.example.labb2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.labb2.model.WeatherDatabase
import com.example.labb2.ui.MainScreen
import com.example.labb2.ui.ScreenPreview
import com.example.labb2.ui.theme.LabB2Theme
import com.example.labb2.viewmodel.WeatherViewModel

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

                    // Instantiate the viewmodel
                    val weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory(db.dao))

                    MainScreen(vm = weatherViewModel/*,onEvent = weatherViewModel::onEvent*/)

                }
            }
        }
    }
}
