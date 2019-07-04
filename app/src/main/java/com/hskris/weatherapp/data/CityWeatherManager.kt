package com.hskris.weatherapp.data

import android.util.Log
import com.hskris.weatherapp.data.api.Api
import com.hskris.weatherapp.data.models.CityWeather
import com.hskris.weatherapp.data.models.Forecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityWeatherManager {

    private val cityId = listOf(1642911, 1277333)
    private val api = Api.getInstance()

    fun fetchCityWeathers(id: Int, callback: ForecastCallback){
        val result = api.getByCityId(id)
        result.enqueue(object : Callback<CityWeather> {
            override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                Log.d("CityWeatherManager", "Error: ${t.message}")
            }

            override fun onResponse(call: Call<CityWeather>, response: Response<CityWeather>) {
                val result = getFiveDaysForecast(response.body()!!)
                callback.onGetForecast(result)
            }

        })
    }

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

interface ForecastCallback {
    fun onGetForecast(forecasts: List<Forecast>)
}