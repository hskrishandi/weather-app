package com.hskris.weatherapp.data.models

class CityWeather(private val id: Int, val name: String, val country: String) {

    private val forecasts: MutableList<Forecast> = mutableListOf()

    fun addForecasts(forecast: Forecast){
        forecasts.add(forecast)
    }
}