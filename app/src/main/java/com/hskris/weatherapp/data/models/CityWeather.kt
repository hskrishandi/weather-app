package com.hskris.weatherapp.data.models

class CityWeather(val city: City) {

    private val forecasts: MutableList<Forecast> = mutableListOf()

    fun addForecasts(forecast: Forecast){
        forecasts.add(forecast)
    }

    fun addForecasts(forecast: List<Forecast>){
        forecasts.addAll(forecast)
    }

    fun getForecasts(): List<Forecast> {
        return forecasts
    }
}