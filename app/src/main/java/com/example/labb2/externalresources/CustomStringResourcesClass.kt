package com.example.labb2.externalresources
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A class which is used to work with different String cases
 */
class CustomStringResourcesClass {
    companion object{

        /**
         * Parses date
         *
         * @param date, the date to parse
         *
         * @return Returns a shortened version of the date
         */
        fun parseDateToString(date: String):String{
            return LocalDateTime.parse(
                date.replace("T"," ").replace("Z"," Z"),
                DateTimeFormatter.ofPattern(DatePatterns.UTC_PATTERN.value())
            )
                .toString().replace("T"," ")
        }

        fun replaceCoordinate(baseURL:String, longitude:Float, latitude:Float) =
            baseURL
                .replace("{first}","$longitude")
                .replace("{second}","$latitude")
    }
}

/**
 * An enum class which represents a date format
 */
enum class DatePatterns(private val pattern: String){

    UTC_PATTERN("yyyy-MM-dd HH:mm:ss z");

    fun value()=this.pattern

}