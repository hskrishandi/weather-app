package com.hskris.weatherapp.models

import java.util.*

class CityWeather(val name: String, val country: String, val timezone: TimeZone) {

    private var weatherInfos: List<WeatherInfo> = emptyList<WeatherInfo>()

}