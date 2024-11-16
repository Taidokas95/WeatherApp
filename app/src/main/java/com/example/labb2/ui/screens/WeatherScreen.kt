package com.example.labb2.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TestP(val title:String,val value:Int)
data class TheListOfWeathers(val approvedDate:String, val approvedTime:String, val listOfWeathers:List<WeatherInfo>)
data class WeatherInfo(val date:String, val time:String ,val weatherIcon:String, val degrees:String)

@Composable
fun WeatherScreen(padding: Modifier) {


    //val highscore by vm.highscore.collectAsState()  // Highscore is its own StateFlow
    val nestedScrollInterop = rememberNestedScrollInteropConnection()
    var label1 by remember { mutableStateOf("") }
    var label2 by remember { mutableStateOf("") }


    /*val weatherInfos = listOf(
        WeatherInfo("2018-06-13", "18:00", "w1", "16.5 °C"),
        WeatherInfo("2018-06-13", "18:00", "w1", "16.5 °C"),
        WeatherInfo("2018-06-13", "18:00", "w1", "16.5 °C"),
        WeatherInfo("2018-06-13", "18:00", "w1", "16.5 °C"),
        WeatherInfo("2018-06-13", "18:00", "w1", "16.5 °C")
    )*/

    val weatherInfos = listOf(
        WeatherInfo("2018-06-13", "18:00", "w1", "16.5 °C")
    )

    val config = Configuration()

    if(config.orientation == Configuration.ORIENTATION_PORTRAIT){
        WeatherScreenPortrait()
    }

    else if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
        WeatherScreenLandScape()
    }


    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.background(Color.Blue).padding(5.dp),
                text = "Weather Forecast",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                modifier = Modifier.background(Color.Blue).padding(5.dp),
                text = "Approved Time: 12345",
                style = MaterialTheme.typography.displayMedium
            )

            Box(
                Modifier.fillMaxWidth().height(150.dp).weight(1f)
            ){
                LazyColumn(
                    modifier = Modifier.nestedScroll(nestedScrollInterop),
                ) {
                    items(weatherInfos){item ->
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .height(60.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                item{
                                    Text(
                                        text = item.date, fontSize = 30.sp
                                    )
                                }
                                item{
                                    Text(
                                        text = item.time,fontSize = 30.sp
                                    )
                                }

                                item{
                                    Text(
                                        text = item.weatherIcon,fontSize = 30.sp
                                    )
                                }

                                item{
                                    Text(
                                        text = item.degrees,fontSize = 30.sp
                                    )
                                }

                            }
                        }

                    }
                }
            }
            // Todo: You'll probably want to change this "BOX" part of the composable
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {

                LazyColumn {
                    item{
                    LazyRow {

                        item {
                            TextField(value = label1,
                                onValueChange = {label1 = it},
                                label = {Text("Latitude")},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            Spacer(Modifier.padding(5.dp))
                        }

                        item {
                            TextField(value = label2,
                                onValueChange = {label2 = it},
                                label = {Text("Longitude")},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(Modifier.padding(5.dp))
                        }
                    }
                    }


                    item {
                            Button(onClick = {}, modifier = Modifier.background(Color.Green,
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
    }
}

@Preview(
    name = "Weather app Screen Preview",
    showBackground = true,
    showSystemUi = true,
    device = "id:medium_phone"
)
@Composable
fun WeatherScreenPreview(){
    //val x = FakeVM()
    //WeatherScreen(androidx.compose.ui.Modifier.Companion.padding(innerPadding))
}