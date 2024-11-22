package com.example.labb2.networkmanager


import com.example.labb2.model.Weather
import com.example.labb2.model.WeatherState
import com.example.labb2.model.WeathersConverter
import com.example.labb2.model.WeathersState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val link1 = "https://maceo.sth.kth.se/weather/forecast?lonLat=lon/14.333/lat/60.383"
const val link2 = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/16/lat/58/data.json"

class RetrofitImp:NetworkService {

    companion object{
        private var retrofit:RetrofitImp? = null
        private var retrofit2:RetrofitClient? = null

        fun createRetroFitImp():RetrofitImp{
            if(retrofit == null) {
                retrofit = RetrofitImp()
                retrofit2 = RetrofitClient
            }
            return retrofit as RetrofitImp
        }

    }

    fun runService(
        //localWeathersState: WeathersState,dao:WeatherDao
        value:RunnableService.RetrofitRunner
    ) = GlobalScope.launch(Dispatchers.Default) {


        when(value.typeOfNetworkService){
            TypeOfNetworkService.GETEXTERNALJSON ->{

            }

            TypeOfNetworkService.MACEOTESTJSON ->{
                runMaceoService(value.localWeathersState)
            }
        }


        withContext(Dispatchers.Main) {
            value.dao.upsertWeather(
                Weather(
                    weathers = WeathersConverter().weathersToString(value.localWeathersState),
                    approvedTime = value.localWeathersState.approvedTime,
                    latitude = value.localWeathersState.latitude!!.toString(),
                    longitude = value.localWeathersState.longitude!!.toString()
                )
            )
        }

    }

    private fun runMaceoService(localWeathersState: WeathersState){
        // Define the URL of the RESTful API you want to call
        val jsonPlaceholderService =
            RetrofitClient.retrofit.create(JsonPlaceholderService::class.java)

        // Make the API call to retrieve the post with ID 1
        val call: Call<TheTestWeather> = jsonPlaceholderService.getWeather("lon/14.333/lat/60.383")

        localWeathersState.latitude = 60.383f
        localWeathersState.longitude = 14.333f

        val response = call.execute()

        try {
            if(response.isSuccessful){
                val post = response.body()

                if(post!=null){
                    println(post.approvedTime)
                    localWeathersState.approvedTime = post.approvedTime

                    for(timeSeries in post.timeSeries){
                        println("size of parameters = ${timeSeries.parameters.size}")
                        println("valid time = ${timeSeries.validTime}")
                        var temperature:Float = -1f
                        var icon = ""
                        for(parameters in timeSeries.parameters){
                            if(parameters.name == "t") temperature = parameters.values[0]
                            else if(parameters.name == "Wsymb2") icon = parameters.values[0].toString()
                        }
                        localWeathersState.weathers.add(
                            WeatherState(timeSeries.validTime, icon, temperature)
                        )
                    }

                }

            }
        }
        catch (t: Throwable){
            println("Error: ${t.message}")
        }
    }

    data class TheTestWeather(val approvedTime:String, val timeSeries:List<Time_Series2>)

    data class Time_Series2(val validTime:String,val parameters: List<Parameters2>)

    data class Parameters2(val name:String, val levelType:String, val level:Int, val unit:String, val values: List<Float>)

    interface JsonPlaceholderService {
        @GET("/weather/forecast")
        fun getWeather(@Query("lonLat") lonLat:String):Call<TheTestWeather>
    }

    object RetrofitClient {
        private const val BASE_URL = "https://maceo.sth.kth.se"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}

// Help structure for network
data class ThePost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

/*fun getWeathersFromDatabase(localWeathersState: WeathersState) = GlobalScope.launch(Dispatchers.Default) {
    // Define the URL of the RESTful API you want to call
    val jsonPlaceholderService =
        RetrofitClient.retrofit.create(JsonPlaceholderService::class.java)

    // Make the API call to retrieve the post with ID 1
    val call: Call<TheTestWeather> = jsonPlaceholderService.getWeather("lon/14.333/lat/60.383")

    localWeathersState.latitude = 60.383f
    localWeathersState.longitude = 14.343f

    val response = call.execute()

    try {
        if(response.isSuccessful){
            val post = response.body()

            if(post!=null){
                println(post.approvedTime)
                localWeathersState.approvedTime = post.approvedTime

                println("size of timeseries = ${post.timeSeries.size}")
                for(timeSeries in post.timeSeries){
                    println("size of parameters = ${timeSeries.parameters.size}")
                    println("valid time = ${timeSeries.validTime}")
                    var temperature:Float = -1f
                    var icon = ""
                    for(parameters in timeSeries.parameters){
                        if(parameters.name == "t") temperature = parameters.values[0]
                        else if(parameters.name == "Wsymb2") icon = parameters.values[0].toString()
                        //println("Name = ${parameters.name}; " +
                        //        "Level type = ${parameters.levelType}; " +
                        //        "Level = ${parameters.level}; " +
                        //        "Unit = ${parameters.unit}; " +
                        //        "Value = ${parameters.values}")
                    }
                    localWeathersState.weathers.add(
                        WeatherState(timeSeries.validTime, icon, temperature)
                    )
                }

            }

        }
    }
    catch (t: Throwable){
        println("Error: ${t.message}")
    }

    withContext(Dispatchers.Main) {
        println(localWeathersState)
    }

}*/
