package com.hskris.weatherapp.data.deserializer

import com.google.gson.*
import java.lang.reflect.Type
import com.hskris.weatherapp.data.models.CityWeather
import com.hskris.weatherapp.data.models.Forecast
import java.util.Date


class ForecastDeserializer : JsonDeserializer<CityWeather> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CityWeather {

        val jsonObject = json as JsonObject
        val cityJson = jsonObject.getAsJsonObject("city")
        val forecastJsonArray = jsonObject.getAsJsonArray("list")

        val cityId = cityJson.get("id").asInt
        val cityName = cityJson.get("name").asString
        val cityCountry = cityJson.get("country").asString

        val cityWeather = CityWeather(cityId, cityName, cityCountry)

        for(f in forecastJsonArray){
            val forecastJson = f as JsonObject

            val date = forecastJson.get("dt").asLong

            val mainJson = forecastJson.getAsJsonObject("main")
            val weatherJson = forecastJson.getAsJsonArray("weather").get(0) as JsonObject

            val temp = mainJson.get("temp").asDouble
            val humidity = mainJson.get("humidity").asInt
            val weather = weatherJson.get("main").asString
            val description = weatherJson.get("description").asString

            val forecast = Forecast(Date(date), temp, humidity, weather, description)

            cityWeather.addForecasts(forecast)
        }

        return cityWeather
    }

}