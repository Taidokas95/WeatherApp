package com.example.labb2

// thread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// Retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

// Parsing JSON
// JSON
import com.google.gson.Gson

// Persistant
import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



// Network test
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.GsonBuilder


// SQLite

class Test (private val application: Application) {


    init{
        println("Init TestFile")
    }

    // Exampel code for threading
    fun threadTest(){
        val backgroundJob = GlobalScope.launch(Dispatchers.Default) {
            // Code to be executed on the background thread
            repeat(5) {
                println("Task on background thread: $it")
            }
            // When your task is complete and you want to update the UI or perform other tasks on the main thread, use Dispatchers.Main
            withContext(Dispatchers.Main) {
                println("Switching to the main thread to update UI or perform other tasks.")
            }
        }

        // Continue executing tasks on the main thread
        repeat(5) {
            println("Task on the main thread: $it")
        }

    }

    // Example code for networking

    // Help structure for network
    data class ThePost(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String
    )

    interface JsonPlaceholderService {
        @GET("/posts/{postId}")
        fun getPost(@Path("postId") postId: Int): Call<ThePost>
    }

    object RetrofitClient {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    fun netDownloadTest() = runBlocking {
        // Define the URL of the RESTful API you want to call
        val jsonPlaceholderService =
            RetrofitClient.retrofit.create(JsonPlaceholderService::class.java)

        // Make the API call to retrieve the post with ID 1
        val call: Call<ThePost> = jsonPlaceholderService.getPost(1)

        // Enqueue the call to run asynchronously
        call.enqueue(object : Callback<ThePost> {
            override fun onResponse(call: Call<ThePost>, response: Response<ThePost>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    if (post != null) {
                        println("Post ID: ${post.id}")
                        println("Title: ${post.title}")
                        println("Body: ${post.body}")
                    } else {
                        println("Response body is null.")
                    }
                } else {
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ThePost>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }

    // Example code for JSON parsing

    // Help structures for Parsing in JSON

    data class Address(val street: String, val city: String, val state: String)

    data class Person(val name: String, val age: Int, val email: String, val address: Address, val interests: List<String>)


    fun JSONparsingTest(){
        val jsonData = """
            {
                "name": "John Doe",
                "age": 30,
                "email": "john.doe@example.com",
                "address": {
                    "street": "123 Main St",
                    "city": "Anytown",
                    "state": "CA"
                },
                "interests": ["reading", "hiking", "coding"]
            }"""

        val gson = Gson()

        val person = gson.fromJson(jsonData, Person::class.java)

        // Access parsed data
        /*println("Name: ${person.name}")
        println("Age: ${person.age}")
        println("Email: ${person.email}")
        println("Address: ${person.address.street}, ${person.address.city}, ${person.address.state}")
        println("Interests: ${person.interests.joinToString(", ")}")
*/
        val gsonPretty = GsonBuilder().setPrettyPrinting().setLenient().create()


        val jsonData2 = gsonPretty.toJson(person)
        val person2 = gsonPretty.fromJson(jsonData2, Person::class.java)

        // Access parsed data
        println("Name: ${person2.name == person.name}")
        println("Age: ${person2.age == person.age}")
        println("Email: ${person2.email == person.email}")
        println("Address: ${person2.address.street == person.address.street}, ${person2.address.city == person.address.city}, ${person2.address.state == person.address.state}")
        println("Interests: ${person2.interests.joinToString(", ")  == person.interests.joinToString(", ")}")


    }

    // Testing persitant using DataStore


    fun Persitant(){
        // Define a constant for the SharedPreferences file name
        // the class is available bellow in the file
       val dataStore = DataStoreManager(application)

        GlobalScope.launch {
            dataStore.saveUsername("Jonas Willén")
        }

        GlobalScope.launch {
            dataStore.getUsername().collect { username ->
                println("Username changed: $username")
            }
        }
    }

    fun isNetworkAvailable() {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val network = connectivityManager?.activeNetwork
        val capabilities = connectivityManager?.getNetworkCapabilities(network)
        if(capabilities != null &&
            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))){
                println("network")
            }else{
                println("no network")
            }
    }

}


class DataStoreManager(context: Context) {
    val Context.dataStore by preferencesDataStore(name = "settings")

    private val dataStore = context.dataStore

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    fun getUsername(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }
    }

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
    }
}
