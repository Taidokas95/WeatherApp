package com.example.labb2.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labb2.model.WeathersState
import com.example.labb2.model.interfaces.WeatherEvent
import com.example.labb2.roommanager.WeatherDao
import com.example.labb2.viewmodel.WeatherViewModel2


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WeatherScreen2(
    onEvent: (WeatherEvent) -> Unit,
    vm: WeatherViewModel2,
    commands: () -> Boolean,
    commands2: (WeathersState) -> Unit
) {

    val weathers by vm.currentListOfWeathers.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var latitude = -1f
    var longitude = -1f

    var label1 by remember { mutableStateOf("") }
    var label2 by remember { mutableStateOf("") }

    val time by vm.currentTime.collectAsState()

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "hello",//text = "${weather.weathers[0].weatherDate} ${weather.weathers[0].weatherIcon} ${weather.weathers[0].temperature} ",
                    fontSize = 20.sp
                )

                Text(
                    text = "hello 2",//text = weather.approvedTime,
                    fontSize = 12.sp
                )

                Button(onClick = {
                    //TODO: Insert cordinates here
                    onEvent(WeatherEvent.SetCoordinates(latitude, longitude, commands, commands2))
                }) {
                    Text(
                        text = "Set",//text = weather.approvedTime,
                        fontSize = 12.sp
                    )
                }

                Button(onClick = {
                    //TODO: Insert cordinates here
                    onEvent(WeatherEvent.SaveWeather)
                }) {
                    Text(
                        text = "Save",//text = weather.approvedTime,
                        fontSize = 12.sp
                    )
                }

                Button(onClick = {

                    //TODO: Insert cordinates here
                    onEvent(WeatherEvent.LoadWeather(latitude, longitude))
                }) {
                    Text(
                        text = "load",//text = weather.approvedTime,
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    LazyColumn {
                        item {
                            LazyRow {

                                item {
                                    TextField(
                                        value = label1,
                                        onValueChange = { label1 = it },
                                        label = { Text("Latitude") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                    Spacer(Modifier.padding(5.dp))
                                }

                                item {
                                    TextField(
                                        value = label2,
                                        onValueChange = { label2 = it },
                                        label = { Text("Longitude") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                    Spacer(Modifier.padding(5.dp))
                                }
                            }
                        }


                        item {
                            Button(
                                onClick = {
                                    latitude = label1.toFloat()
                                    longitude = label2.toFloat()
                                    onEvent(
                                        WeatherEvent.SetCoordinates(
                                            latitude,
                                            longitude,
                                            commands,
                                            commands2
                                        )
                                    )

                                    //WeatherEvent.SaveWeather
                                },
                                modifier = Modifier.background(
                                    Color.Green,
                                    RoundedCornerShape(5.dp)
                                )) {
                                Text(
                                    text = "Submit"
                                )
                            }
                            Spacer(Modifier.padding(16.dp))
                        }
                    }


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
