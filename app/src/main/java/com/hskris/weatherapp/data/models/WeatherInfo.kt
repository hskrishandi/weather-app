package com.hskris.weatherapp.data.models

import java.util.*

data class WeatherInfo(
    val date: Date,
    val temp: String,
    val humidity: String,
    val weather: String,
    val description: String
)