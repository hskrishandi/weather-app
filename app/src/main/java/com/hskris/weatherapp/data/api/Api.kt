package com.hskris.weatherapp.data.api

import com.google.gson.GsonBuilder
import com.hskris.weatherapp.data.deserializer.ForecastDeserializer
import com.hskris.weatherapp.data.models.CityWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {

    companion object {
        const val API_KEY = "fe639a36ccf927f530f55a568977c9ca"
        private val API_URL = "http://api.openweathermap.org/data/2.5/"
        private var retrofit: Retrofit? = null

        fun getInstance(): ApiService {
            if (retrofit == null) {
                val weatherDeserializer =
                    GsonBuilder().registerTypeAdapter(CityWeather::class.java, ForecastDeserializer()).create()

                retrofit = retrofit2.Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create(weatherDeserializer))
                    .baseUrl(API_URL)
                    .build()
            }
            return retrofit!!.create(ApiService::class.java)
        }
    }
}