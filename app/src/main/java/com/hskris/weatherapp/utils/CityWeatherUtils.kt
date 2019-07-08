package com.hskris.weatherapp.utils

import com.hskris.weatherapp.data.models.CityWeather
import com.hskris.weatherapp.data.models.Forecast

class CityWeatherUtils {

    companion object {
        fun getFiveDaysForecast(cityWeather: CityWeather): List<Forecast>{

            val forecasts = cityWeather.getForecasts()

            val selectedForecast = mutableListOf<Forecast>()
            for (i in 0..4) {
                val index = i * 8
                if (index < forecasts.size)
                    selectedForecast.add(forecasts[index])
            }

            return selectedForecast
        }
    }
}