package com.example.labb2.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labb2.R
import com.example.labb2.viewmodel.FakeVM
import com.example.labb2.model.interfaces.WeatherEvent
import com.example.labb2.viewmodel.WeatherViewModelInterface
import kotlin.reflect.KFunction1

data class WeatherInfo(val date: String, val time: String, val type: String, val degrees: String)

@Composable
fun MainScreen(/*onEvent: (WeatherEvent) -> Unit,*/ vm: WeatherViewModelInterface) {
    val orientation = LocalConfiguration.current.orientation

    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeLayout(/*onEvent,*/ vm)
    } else {
        PortraitLayout(/*onEvent,*/ vm)
    }
}

@Composable
fun LandscapeLayout(/*onEvent: (WeatherEvent) -> Unit,*/ vm: WeatherViewModelInterface) {
    val weatherInfos = listOf(
        WeatherInfo("2018-06-13", "12:00", "sunny", "20"),
        WeatherInfo("2018-06-14", "12:00", "cloudy", "18"),
        WeatherInfo("2018-06-15", "12:00", "windy", "17"),
        WeatherInfo("2018-06-16", "12:00", "rainy", "16"),
        WeatherInfo("2018-06-17", "12:00", "sunny", "19"),
        WeatherInfo("2018-06-18", "12:00", "rainy", "16"),
        WeatherInfo("2018-06-19", "12:00", "sunny", "19")
    )


    val snackBarHostState = remember { SnackbarHostState() }
    var lon by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }
    var weatherLists = vm.currentListOfWeathers.collectAsState()

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, Color(100,172,255)),
                        start = Offset(400f, 50f),
                        end = Offset.Infinite
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Weather Forecast",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Location: $lon, $lat",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        userScrollEnabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        items(weatherInfos.size) { index ->
                            var icon: Int
                            when (weatherInfos.get(index).type) {
                                "rainy" -> icon = R.drawable.rain
                                "cloudy" -> icon = R.drawable.overcast
                                "windy" -> icon = R.drawable.wind
                                else -> icon = R.drawable.sun
                            }
                            /*var weatherType = ${weatherLists.weather[index].weatherIcon}
                            var icon = R.drawable.sun
                            when (icon) {
                                1 -> icon = R.drawable.sun //clear skies
                                2 -> icon = R.drawable.cloud //partly cloudy
                                3 -> icon = R.drawable.cloud //cloudy
                                4 -> icon = R.drawable.overcast //overcast
                                6 -> icon = R.drawable.rain //rain showers
                                7 -> icon = R.drawable.snow //snow showers
                                8 -> icon = R.drawable.thunder
                                9 -> icon = R.drawable.rain //rain
                                10 -> icon = R.drawable.snow //snow
                                11 -> icon = R.drawable.rain//freezing rain
                                12 -> icon = R.drawable.rain //drizzle
                                13 -> icon = R.drawable.rain//freezing drizzle
                                14 -> icon = R.drawable.thunder //thunder with rain
                                15 -> icon = R.drawable.thunder //thunder with snow
                                else -> icon = R.drawable.sun
                            }*/
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .weight(1f)
                                        .wrapContentSize(Alignment.Center)
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = "weather type",
                                        modifier = Modifier.size(32.dp, 32.dp)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .weight(1f)
                                        .wrapContentSize(Alignment.Center)
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).date, //${weatherLists.weather[index].date}
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .weight(1f)
                                        .wrapContentSize(Alignment.Center)
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).time, //${weatherLists.weather[index].time}
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .weight(1f)
                                        .wrapContentSize(Alignment.Center)
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).degrees + " C", //${weatherLists.weathers[index].temperature}
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(64.dp)
                                    .weight(1f)
                            )
                            {
                                TextField(
                                    value = lon,
                                    onValueChange = { lon = it },
                                    label = {
                                        Text(
                                            text = "Longitude",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    },
                                    maxLines = 1,
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedLabelColor = Color.DarkGray,
                                        unfocusedLabelColor = Color.DarkGray
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .height(64.dp)
                                    .weight(1f)
                            )
                            {
                                TextField(
                                    value = lat,
                                    onValueChange = { lon = it },
                                    label = {
                                        Text(
                                            text = "Latitude",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    },
                                    maxLines = 1,
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedLabelColor = Color.DarkGray,
                                        unfocusedLabelColor = Color.DarkGray
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    //onEvent(WeatherEvent.LoadWeather)
                                },
                                modifier = Modifier
                                    .size(100.dp, 60.dp)
                                    .border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) {
                                Text(
                                    text = "Load",//text = weather.approvedTime,
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(64.dp))
                            Button(
                                onClick = {
                                    vm.setLongitude(lon.toFloat())
                                    vm.setLatitude(lat.toFloat())
                                    //onEvent(WeatherEvent.setCoordinates(""))
                                },
                                modifier = Modifier
                                    .size(100.dp, 60.dp)
                                    .border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) {
                                Text(
                                    text = "Set",//text = weather.approvedTime,
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitLayout(/*onEvent: (WeatherEvent) -> Unit,*/ vm: WeatherViewModelInterface) {
    val weatherInfos = listOf(
        WeatherInfo("2018-06-13", "12:00", "sunny", "20"),
        WeatherInfo("2018-06-14", "12:00", "cloudy", "18"),
        WeatherInfo("2018-06-15", "12:00", "windy", "17"),
        WeatherInfo("2018-06-16", "12:00", "rainy", "16"),
        WeatherInfo("2018-06-17", "12:00", "sunny", "19"),
        WeatherInfo("2018-06-18", "12:00", "rainy", "16"),
        WeatherInfo("2018-06-19", "12:00", "sunny", "19")
    )

    val weathers by vm.currentListOfWeathers.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var lon by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White,  Color(100,172,255)),
                        start = Offset(400f, 50f),
                        end = Offset.Infinite
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(56.dp))
                    Text(
                        text = "Weather Forecast",//text = "${weather.weathers[0].weatherDate} ${weather.weathers[0].weatherIcon} ${weather.weathers[0].temperature} ",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Location: Stockholm",//text = weather.location,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    LazyColumn {
                        items(weatherInfos.size) { index ->
                            var icon: Int
                            when (weatherInfos.get(index).type) {
                                "rainy" -> icon = R.drawable.rain
                                "cloudy" -> icon = R.drawable.overcast
                                "windy" -> icon = R.drawable.wind
                                else -> icon = R.drawable.sun
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = "weather type",
                                        modifier = Modifier.size(32.dp, 32.dp)
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).date,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).time,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).degrees + " C",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(64.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = lon,
                            onValueChange = { lon = it },
                            label = {
                                Text(
                                    text = "Longitude",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            },
                            maxLines = 1,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedLabelColor = Color.DarkGray,
                                unfocusedLabelColor = Color.DarkGray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextField(
                            value = lat,
                            onValueChange = { lon = it },
                            label = {
                                Text(
                                    text = "Latitude",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            },
                            maxLines = 1,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedLabelColor = Color.DarkGray,
                                unfocusedLabelColor = Color.DarkGray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(64.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                            //onEvent(WeatherEvent.LoadWeather)
                            },
                            modifier = Modifier
                                .size(100.dp, 60.dp)
                                .border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                text = "Load",//text = weather.approvedTime,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                        Button(
                            onClick = {
                                vm.setLongitude(lon.toFloat())
                                vm.setLatitude(lat.toFloat())
                                //onEvent(WeatherEvent.setCoordinates(""))
                            },
                            modifier = Modifier
                                .size(100.dp, 60.dp)
                                .border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                text = "Set",//text = weather.approvedTime,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(
    name = "Weather app Screen Preview",
    uiMode = Configuration.ORIENTATION_LANDSCAPE, // Forcing landscape orientation
    widthDp = 800,
    heightDp = 400,
    showBackground = true,
    //showSystemUi = true,
    //device = "id:medium_phone",
)

@Composable
fun ScreenPreview(
) {
    Surface { LandscapeLayout(FakeVM()) }
}