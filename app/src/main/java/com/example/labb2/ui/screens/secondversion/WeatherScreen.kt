package com.example.labb2.ui.screens.secondversion

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labb2.model.secondversion.databasemymanager.interfaces.WeatherEvent
import com.example.labb2.viewmodel.secondversion.WeatherViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(onEvent: (WeatherEvent) -> Unit, vm: WeatherViewModel){

    val weathers by vm.currentListOfWeathers.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }){

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "hello",//text = "${weather.weathers[0].weatherDate} ${weather.weathers[0].weatherIcon} ${weather.weathers[0].temperature} ",
                    fontSize = 20.sp
                )

                Text(
                    text = "hello 2",//text = weather.approvedTime,
                    fontSize = 12.sp)

                Button(onClick = {
                    //TODO: Insert cordinates here
                    onEvent(WeatherEvent.setCoordinates(""))
                }) {
                    Text(
                        text = "Set",//text = weather.approvedTime,
                        fontSize = 12.sp)
                }

                Button(onClick = {
                    //TODO: Insert cordinates here
                    onEvent(WeatherEvent.SaveWeather)
                }) {
                    Text(
                        text = "Save",//text = weather.approvedTime,
                        fontSize = 12.sp)
                }

                Button(onClick = {
                    //TODO: Insert cordinates here
                    onEvent(WeatherEvent.LoadWeather)
                }) {
                    Text(
                        text = "load",//text = weather.approvedTime,
                        fontSize = 12.sp)
                }



            }

            /*
            items(weathers.weathers) { weather ->
                Row (modifier =  Modifier.fillMaxWidth()){

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "hello",//text = "${weather.weathers[0].weatherDate} ${weather.weathers[0].weatherIcon} ${weather.weathers[0].temperature} ",
                            fontSize = 20.sp
                        )

                        Text(
                            text = "hello 2",//text = weather.approvedTime,
                            fontSize = 12.sp)

                        Button(onClick = {
                            //TODO: Insert cordinates here
                            onEvent(WeatherEvent.setCoordinates(""))
                        }) {
                            Text(
                                text = "Set",//text = weather.approvedTime,
                                fontSize = 12.sp)
                        }

                        Button(onClick = {
                            //TODO: Insert cordinates here
                            onEvent(WeatherEvent.SaveWeather)
                        }) {
                            Text(
                                text = "Save",//text = weather.approvedTime,
                                fontSize = 12.sp)
                        }

                        Button(onClick = {
                            //TODO: Insert cordinates here
                            onEvent(WeatherEvent.LoadWeather)
                        }) {
                            Text(
                                text = "load",//text = weather.approvedTime,
                                fontSize = 12.sp)
                        }



                    }

                }
            }
        */
        }

    }

}
