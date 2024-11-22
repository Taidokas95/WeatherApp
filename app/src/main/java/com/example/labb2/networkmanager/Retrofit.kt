package com.example.labb2.networkmanager


import com.example.labb2.CustomStringResourcesClass
import com.example.labb2.model.WeatherState
import com.example.labb2.model.WeathersState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
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

    fun runService(localWeathersState: WeathersState) = GlobalScope.launch(Dispatchers.Default) {
        // Define the URL of the RESTful API you want to call
        val jsonPlaceholderService =
            //RetrofitClient.retrofit.create(JsonPlaceholderService::class.java)
            RetrofitClient.createRetrofit("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/").create(JsonPlaceholderService::class.java)

        // Make the API call to retrieve the post with ID 1
        //val call: Call<TheTestWeather> = jsonPlaceholderService.getWeather("lon/14.333/lat/60.383")
        val call: Call<TheTestWeather> = jsonPlaceholderService.getWeather2(
            localWeathersState.longitude.toString(),localWeathersState.latitude.toString()
            //"lon/${localWeathersState.longitude.toString()}/lat/${localWeathersState.latitude.toString()}"
        )

        //localWeathersState.latitude = 60.383f
        //localWeathersState.longitude = 14.343f

        val response = call.execute()

        try {
            if(response.isSuccessful){
                val post = response.body()

                if(post!=null){
                    println(post.approvedTime)
                    localWeathersState.approvedTime = CustomStringResourcesClass.parseDateToString(post.approvedTime)//post.approvedTime

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
                            WeatherState(CustomStringResourcesClass.parseDateToString(timeSeries.validTime), icon, temperature)
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

    }

    data class TheTestWeather(val approvedTime:String, val timeSeries:List<Time_Series2>)

    data class Time_Series2(val validTime:String,val parameters: List<Parameters2>)

    data class Parameters2(val name:String, val levelType:String, val level:Int, val unit:String, val values: List<Float>)

    interface JsonPlaceholderService {
        @GET("/weather/forecast")
        fun getWeather(@Query("lonLat") lonLat:String):Call<TheTestWeather>

        @GET("lon/{lonId}/lat/{latId}/data.json")
        fun getWeather2(@Path("lonId") lonId: String, @Path("latId") latId:String):Call<TheTestWeather>

    }

    object RetrofitClient {
        private const val BASE_URL = "https://maceo.sth.kth.se"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun createRetrofit(baseUrl:String) = Retrofit.Builder()
            .baseUrl(baseUrl)
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
