package com.example.labb2
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomStringResourcesClass {
    companion object{
        fun parseDateToString(date: String):String{
            return LocalDateTime.parse(
                date.replace("T"," ").replace("Z"," Z"),
                DateTimeFormatter.ofPattern(DatePatterns.UTC_PATTERN.value())
            )
                .toString().replace("T"," ")
        }

        //TODO: replace first and second with something else?
        fun replaceCoordinate(baseURL:String, longitude:Float, latitude:Float) =
            baseURL
                .replace("{first}","$longitude")
                .replace("{second}","$latitude")
    }
}

enum class DatePatterns(private val pattern: String){

    UTC_PATTERN("yyyy-MM-dd HH:mm:ss z");

    fun value()=this.pattern

}