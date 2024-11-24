package com.example.labb2.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labb2.ui.CustomComposables.DrawIconFromIdComposable
import com.example.labb2.externalresources.CustomStringResourcesClass
import com.example.labb2.R
import com.example.labb2.model.WeathersState
import com.example.labb2.model.interfaces.WeatherEvent
import com.example.labb2.viewmodel.WeatherViewModel2
import kotlinx.coroutines.launch

data class WeatherInfo(val date: String, val time: String, val type: String, val degrees: String)

@Composable
fun MainScreen(
    onEvent: (WeatherEvent) -> Unit, vm: WeatherViewModel2, commands: () -> Boolean,
    commands2: (WeathersState) -> Pair<Boolean,String>
) {
    val orientation = LocalConfiguration.current.orientation

    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeLayout(onEvent, vm, commands, commands2)
    } else {
        PortraitLayout(onEvent, vm, commands, commands2)
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LandscapeLayout(
    onEvent: (WeatherEvent) -> Unit, vm: WeatherViewModel2, commands: () -> Boolean,
    commands2: (WeathersState) -> Pair<Boolean,String>
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var lon by remember { mutableStateOf("14.333") }
    var lat by remember { mutableStateOf("60.383") }
    //val weatherLists = vm.currentListOfWeathers.collectAsState()
    val weatherLists by vm.currentListOfWeathers.collectAsState()

    val scope = rememberCoroutineScope()

    /*var weatherType = "type"
    var date = "date"
    var time = "time"
    var temperature = 1f
    var icon = R.drawable.sun*/

    val isUpdated by vm.isUpdated.collectAsState()

    val theRetrofitMessage by vm.theRetrofitMessage.collectAsState()

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, Color(100, 172, 255)),
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
                        text = stringResource(R.string.weather_forecast),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(R.string.location_lon_lat, lon, lat),
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if(weatherLists.approvedTime!= "")
                        Text(
                            text = CustomStringResourcesClass.parseDateToString(weatherLists.approvedTime),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )

                    Spacer(modifier = Modifier.height(16.dp))
                    if(!theRetrofitMessage.first && theRetrofitMessage.second == "404"){
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Requested point is out of bounds"
                        )
                    }
                    vm.updateRetrofitMessage(true,"Success")
                }

                    else if(!theRetrofitMessage.first && theRetrofitMessage.second == "No internet"){
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "No internet connection"
                            )
                        }
                        vm.updateRetrofitMessage(true,"Success")
                    }

                    else if(!theRetrofitMessage.first && theRetrofitMessage.second == "No content"){
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "No internet and no weather with such coordinates exists"
                            )
                        }
                        vm.updateRetrofitMessage(true,"Success")
                    }

                    else if(weatherLists.weathers.size > 0 || isUpdated) {
                        LazyColumn(
                            userScrollEnabled = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            state =rememberLazyListState()
                        ) {

                            vm.updateState(false)

                            items(weatherLists.weathers.size, itemContent = { index ->
                                //var weatherType: String
                                //var date = ""
                                //var time = ""
                                //var temperature = -1f
                                //var icon = R.drawable.sun

                                LaunchedEffect(weatherLists) {
                                    /*weatherType = weatherLists.weathers[index].weatherIcon
                                    date = weatherLists.weathers[index].weatherDate
                                    time = weatherLists.approvedTime
                                    temperature = weatherLists.weathers[index].temperature*/

                                    /*when (weatherType) {
                                        "1" -> icon = R.drawable.sun //clear skies
                                        "2" -> icon = R.drawable.cloud //partly cloudy
                                        "3" -> icon = R.drawable.cloud //cloudy
                                        "4" -> icon = R.drawable.overcast //overcast
                                        "5" -> icon = R.drawable.fog //fog
                                        "6" -> icon = R.drawable.rain //rain showers
                                        "7" -> icon = R.drawable.snow //snow showers
                                        "8" -> icon = R.drawable.thunder
                                        "9" -> icon = R.drawable.rain //rain
                                        "10" -> icon = R.drawable.snow //snow
                                        "11" -> icon = R.drawable.rain//freezing rain
                                        "12" -> icon = R.drawable.rain //drizzle
                                        "13" -> icon = R.drawable.rain//freezing drizzle
                                        "14" -> icon = R.drawable.thunder //thunder with rain
                                        "15" -> icon = R.drawable.thunder //thunder with snow
                                        else -> icon = R.drawable.sun
                                    }*/
                                }

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
                                            painter = painterResource(
                                                id = DrawIconFromIdComposable(weatherLists.weathers[index].weatherIcon)//id = icon
                                            ),
                                            contentDescription = "weather type",
                                            modifier = Modifier.size(32.dp, 32.dp)
                                        )
                                    }
                                    Box(
                                        modifier = Modifier.height(32.dp).weight(1f)
                                            .wrapContentSize(Alignment.Center)
                                            .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                    ) {
                                        Text(
                                            text = CustomStringResourcesClass.parseDateToString(weatherLists.weathers[index].weatherDate),//date,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    /*Box(
                                        modifier = Modifier.height(32.dp).weight(1f)
                                            .wrapContentSize(Alignment.Center)
                                            .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                    ) {
                                        Text(
                                            text = weatherLists.approvedTime,//time,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }*/
                                    Box(
                                        modifier = Modifier.height(32.dp).weight(1f)
                                            .wrapContentSize(Alignment.Center)
                                            .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                    ) {
                                        Text(
                                            text = stringResource(
                                                R.string.Celsius,
                                                weatherLists.weathers[index].temperature
                                                //temperature
                                            ),
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            })
                        }
                    }
                    // TODO: END MARKER HERE

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier.height(64.dp).weight(1f)
                            )
                            {
                                TextField(
                                    value = lon,
                                    onValueChange = { lon = it },
                                    label = { Text(text = stringResource(R.string.Longitude), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    maxLines = 1,
                                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
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
                                modifier = Modifier.height(64.dp).weight(1f)
                            )
                            {
                                TextField(
                                    value = lat,
                                    onValueChange = { lon = it },
                                    label = {
                                        Text(text = stringResource(R.string.Latitude), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium))
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    maxLines = 1,
                                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedLabelColor = Color.DarkGray,
                                        unfocusedLabelColor = Color.DarkGray
                                    ),
                                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            /*Button(
                                onClick = {
                                    onEvent(WeatherEvent.LoadWeather(lat.toFloat(), lon.toFloat()))
                                },
                                modifier = Modifier.size(100.dp, 60.dp).border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) {
                                Text(text = stringResource(R.string.Load), fontSize = 20.sp, color = Color.Black)
                            }*/

                            Spacer(modifier = Modifier.width(64.dp))

                            Button(
                                onClick = {
                                    try{
                                        onEvent(
                                            WeatherEvent.SetCoordinates(lat.toFloat(), lon.toFloat(), commands, commands2)
                                        )
                                    } catch (e:NumberFormatException){
                                        scope.launch {
                                            snackBarHostState.showSnackbar(
                                                message = "Input not float"
                                            )
                                        }
                                    }

                                },
                                modifier = Modifier.size(100.dp, 60.dp).border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) {
                                Text(text = stringResource(R.string.Set), fontSize = 20.sp, color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PortraitLayout(
    onEvent: (WeatherEvent) -> Unit, vm: WeatherViewModel2, commands: () -> Boolean,
    commands2: (WeathersState) -> Pair<Boolean,String>
) {
    val weatherLists by vm.currentListOfWeathers.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var lon by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    /*var weatherType = "type"
    var date = "date"
    var time = "time"
    var temperature = 1f
    var icon = R.drawable.sun*/

    val isUpdated by vm.isUpdated.collectAsState()

    val theRetrofitMessage by vm.theRetrofitMessage.collectAsState()

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
        Box(
            modifier = Modifier.fillMaxSize().background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, Color(100, 172, 255)),
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
                    Text(text = stringResource(R.string.weather_forecast), fontSize = 32.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(R.string.location_lon_lat, lon, lat), fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(32.dp))
                    if(weatherLists.approvedTime!= "")
                        Text(
                            text = CustomStringResourcesClass.parseDateToString(weatherLists.approvedTime),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    Spacer(modifier = Modifier.height(8.dp))

                    if(!theRetrofitMessage.first && theRetrofitMessage.second == "404"){
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Requested point is out of bounds"
                        )
                    }
                    vm.updateRetrofitMessage(true,"Success")
                }

                    else if(!theRetrofitMessage.first && theRetrofitMessage.second == "No internet"){
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "No internet connection"
                            )
                        }
                        vm.updateRetrofitMessage(true,"Success")
                    }

                    else if(!theRetrofitMessage.first && theRetrofitMessage.second == "No content"){
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "No internet and no weather with such coordinates exists"
                            )
                        }
                        vm.updateRetrofitMessage(true,"Success")
                    }


                    else if(weatherLists.weathers.size > 0 || isUpdated){
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp),
                            state =rememberLazyListState()
                        ) {

                            vm.updateState(false)

                            items(weatherLists.weathers.size, itemContent = { index -> //weatherLists.weathers.size
                                println("Size of weather report = ${weatherLists.weathers.size}")
                            //var weatherType = "type"
                            //var date = "date"
                            //var time = "time"
                            //var temperature = 1f
                            //var icon = R.drawable.sun

                                /*weatherType = weatherLists.weathers[index].weatherIcon
                                date = weatherLists.weathers[index].weatherDate
                                time = weatherLists.approvedTime
                                temperature = weatherLists.weathers[index].temperature*/


                                /*when (weatherType) {
                                "1" -> icon = R.drawable.sun //clear skies
                                "2" -> icon = R.drawable.cloud //partly cloudy
                                "3" -> icon = R.drawable.cloud //cloudy
                                "4" -> icon = R.drawable.overcast //overcast
                                "5" -> icon = R.drawable.fog //fog
                                "6" -> icon = R.drawable.rain //rain showers
                                "7" -> icon = R.drawable.snow //snow showers
                                "8" -> icon = R.drawable.thunder
                                "9" -> icon = R.drawable.rain //rain
                                "10" -> icon = R.drawable.snow //snow
                                "11" -> icon = R.drawable.rain//freezing rain
                                "12" -> icon = R.drawable.rain //drizzle
                                "13" -> icon = R.drawable.rain//freezing drizzle
                                "14" -> icon = R.drawable.thunder //thunder with rain
                                "15" -> icon = R.drawable.thunder //thunder with snow
                                else -> icon = R.drawable.sun
                            }*/

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
                                        painter = painterResource(
                                            id = DrawIconFromIdComposable(weatherLists.weathers[index].weatherIcon)
                                            //weatherLists.weathers[index].weatherIcon.toInt()//icon
                                        ),
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
                                        text = CustomStringResourcesClass.parseDateToString(weatherLists.weathers[index].weatherDate),//date,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                /*Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = weatherLists.approvedTime,//time,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }*/
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(
                                            R.string.Celsius, weatherLists.weathers[index].temperature//temperature
                                        ),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        })

                        }
                    }

                    Spacer(modifier = Modifier.height(64.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = lon,
                            onValueChange = { lon = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.Longitude),
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            onValueChange = { lat = it },
                            label = {
                                Text(
                                    text = stringResource(R.string.Latitude),
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                        /*Button(
                            onClick = {
                                onEvent(WeatherEvent.LoadWeather(lat.toFloat(), lon.toFloat()))
                            },
                            modifier = Modifier
                                .size(100.dp, 60.dp)
                                .border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                text = stringResource(R.string.Load),//"Load",
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }*/
                        Button(
                            onClick = {
                                //onEvent(WeatherEvent.SetCoordinates(lat.toFloat(), lon.toFloat(), commands, commands2))
                                try{
                                    onEvent(WeatherEvent.SetCoordinates(lat.toFloat(), lon.toFloat(), commands, commands2))
                                } catch (e:NumberFormatException){
                                    scope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Input not float"
                                        )
                                    }
                                }

                            },
                            modifier = Modifier
                                .size(100.dp, 60.dp)
                                .border(2.dp, Color.Black, RoundedCornerShape(32.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                text = stringResource(R.string.Set),
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
    //Surface { LandscapeLayout(FakeVM()) }
}