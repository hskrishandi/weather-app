package com.hskris.weatherapp.data.models

import java.util.*

data class WeatherInfo(
    var date: Date?,
    var temp: Double,
    var humidity: Int,
    var weather: String,
    var description: String
)