package com.example.labb2.externalresources.networkmanager


import com.example.labb2.exceptions.OutOfBoundsException
import com.example.labb2.model.WeatherState
import com.example.labb2.model.WeathersState
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val link1 = "https://maceo.sth.kth.se/weather/forecast?lonLat=lon/14.333/lat/60.383"
const val link2 =
    "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/16/lat/58/data.json"

/**
 * A class which represents a network service which can be used.
 *
 * It represents an class which allows the developer to run a service with Retrofit2
 */
class RetrofitImp : NetworkService {

    companion object {
        private var retrofit: RetrofitImp? = null
        private var retrofit2: RetrofitClient? = null

        /**
         * Get a retrofit implementation singleton
         *
         * @return Returns an instance of the Retrofit implementation
         */
        fun createRetroFitImp(): RetrofitImp {
            if (retrofit == null) {
                retrofit = RetrofitImp()
                retrofit2 = RetrofitClient
            }
            return retrofit as RetrofitImp
        }
    }

    /**
     * A service which can be called.
     *
     * It is represented by two services:
     *      A test service for accessing the maceo database
     *      A service for accessing an external json file
     */
    fun runService(value: RunnableService.RetrofitRunner) {
        when (value.typeOfNetworkService) {
            TypeOfNetworkService.GETEXTERNALJSON -> {
                runSMHIService(value.localWeathersState)
            }

            TypeOfNetworkService.MACEOTESTJSON -> {
                runMaceoService(value.localWeathersState)
            }
        }
    }
}

// A service which can access the SMHI database
private fun runSMHIService(localWeathersState: WeathersState) {

    // Define the URL of the RESTful API you want to call
    val jsonPlaceholderService =
        RetrofitClient.createRetrofit("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/")
            .create(JsonPlaceholderService::class.java)

    // Represents the call class
    val call: Call<TheTestWeather> = jsonPlaceholderService.getWeather2(
        localWeathersState.longitude.toString(), localWeathersState.latitude.toString()
    )

    val response = call.execute()   // Executes the call synchronously
    println(response.code())        // Prints the response code

    if (response.code() == 404) throw OutOfBoundsException()   // Throw an exception when coordinates are out of bounds

    // If successful, set the localWeatherState with the json data stored in the database
    if (response.isSuccessful) {
        val post = response.body()

        if (post != null) {
            localWeathersState.approvedTime = post.approvedTime

            for (timeSeries in post.timeSeries) {
                var temperature: Float = -1f
                var icon = ""
                for (parameters in timeSeries.parameters) {
                    if (parameters.name == "t") temperature = parameters.values[0]
                    else if (parameters.name == "Wsymb2") icon = parameters.values[0].toString()
                }
                //println(icon)

                // Add a new weather state to the WeathersState class
                localWeathersState.weathers.add(
                    WeatherState(timeSeries.validTime, icon, temperature)
                )
            }

        } else {
            println("Error: ${response.code()}")
        }
    }
}

private fun runMaceoService(localWeathersState: WeathersState) {
    // Define the URL of the RESTful API you want to call
    val jsonPlaceholderService =
        RetrofitClient.retrofit.create(JsonPlaceholderService::class.java)

    // Make the API call to retrieve the post with ID 1
    val call: Call<TheTestWeather> = jsonPlaceholderService.getWeather("lon/14.333/lat/60.383")

    localWeathersState.latitude = 60.383f
    localWeathersState.longitude = 14.333f

    val response = call.execute()

    if (response.isSuccessful) {
        val post = response.body()

        if (post != null) {
            //println(post.approvedTime)
            localWeathersState.approvedTime = post.approvedTime

            for (timeSeries in post.timeSeries) {
                var temperature: Float = -1f
                var icon = ""
                for (parameters in timeSeries.parameters) {
                    if (parameters.name == "t") temperature = parameters.values[0]
                    else if (parameters.name == "Wsymb2") icon = parameters.values[0].toString()
                }
                localWeathersState.weathers.add(
                    WeatherState(timeSeries.validTime, icon, temperature)
                )
            }
        }
    }
}

// Help structure for network
data class TheTestWeather(val approvedTime: String, val timeSeries: List<Time_Series2>)

// The time series
data class Time_Series2(val validTime: String, val parameters: List<Parameters2>)

// The parameters stored inside the time series
data class Parameters2(
    val name: String,
    val levelType: String,
    val level: Int,
    val unit: String,
    val values: List<Float>
)

/**
 *
 * Defines a json service which is used to gain access to the used databases
 *
 */
interface JsonPlaceholderService {
    @GET("/weather/forecast")
    fun getWeather(@Query("lonLat") lonLat: String): Call<TheTestWeather>

    @GET("lon/{lonId}/lat/{latId}/data.json")
    fun getWeather2(
        @Path("lonId") lonId: String,
        @Path("latId") latId: String
    ): Call<TheTestWeather>
}

object RetrofitClient {
    private const val BASE_URL = "https://maceo.sth.kth.se"

    // Builder for building a retrofit instance
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     *
     * Creates a retrofit instance with a baseURL
     *
     * @param baseUrl, the baseUrl used for the retrofit service
     *
     * @return returns a retrofit instance with a specific base url
     *
     */
    fun createRetrofit(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}