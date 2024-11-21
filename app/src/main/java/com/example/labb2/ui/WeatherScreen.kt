package com.example.labb2.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labb2.R
import com.example.labb2.model.interfaces.WeatherEvent
import com.example.labb2.viewmodel.WeatherViewModelInterface
import kotlin.reflect.KFunction1

data class WeatherInfo(val date: String, val time: String, val type: String, val degrees: String)

@Composable
fun MainScreen(onEvent: (WeatherEvent) -> Unit, vm: WeatherViewModelInterface) {
    val orientation = LocalConfiguration.current.orientation

    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeLayout(onEvent, vm)
    } else {
        PortraitLayout(onEvent, vm)
    }
}

@Composable
fun LandscapeLayout(onEvent: (WeatherEvent) -> Unit, vm: WeatherViewModelInterface) {
    Row {
        // Landscape-specific layout
    }
}

@Composable
fun PortraitLayout(onEvent: (WeatherEvent) -> Unit, vm: WeatherViewModelInterface) {

    val weathers by vm.currentListOfWeathers.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val weatherInfos = listOf(
        WeatherInfo("2018-06-13", "12:00", "sunny", "20"),
        WeatherInfo("2018-06-14", "12:00", "cloudy", "18"),
        WeatherInfo("2018-06-15", "12:00", "windy", "17"),
        WeatherInfo("2018-06-16", "12:00", "rainy", "16"),
        WeatherInfo("2018-06-17", "12:00", "sunny", "19")
    )

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, Color.Blue),
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
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Weather Forecast",//text = "${weather.weathers[0].weatherDate} ${weather.weathers[0].weatherIcon} ${weather.weathers[0].temperature} ",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                                "cloudy" -> icon = R.drawable.cloud
                                "windy" -> icon = R.drawable.wind
                                else -> icon = R.drawable.sun
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = "weather type"
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).date,
                                        fontSize = 16.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).time,
                                        fontSize = 16.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = weatherInfos.get(index).degrees + " C",
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                    Button(onClick = {
                        //TODO: Insert cordinates here
                        //onEvent(WeatherEvent.setCoordinates(""))
                    }) {
                        Text(
                            text = "Set",//text = weather.approvedTime,
                            fontSize = 12.sp
                        )
                    }

                    Button(onClick = {
                        //TODO: Insert cordinates here
                        //onEvent(WeatherEvent.SaveWeather)
                    }) {
                        Text(
                            text = "Save",//text = weather.approvedTime,
                            fontSize = 12.sp
                        )
                    }

                    Button(onClick = {
                        //TODO: Insert cordinates here
                        //onEvent(WeatherEvent.LoadWeather)
                    }) {
                        Text(
                            text = "load",//text = weather.approvedTime,
                            fontSize = 12.sp
                        )
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
fun ScreenPreview(
) {
    //Surface { PortraitLayout(FakeVM()) }
}