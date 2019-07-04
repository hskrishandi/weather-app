package com.hskris.weatherapp.data.models

import java.util.*

data class Forecast(
    val date: Date?,
    val temp: Double,
    val humidity: Int,
    val weather: String,
    val description: String
)