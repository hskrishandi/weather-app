package com.hskris.weatherapp.data.repository

import android.util.Log
import com.hskris.weatherapp.data.api.Api
import com.hskris.weatherapp.data.models.CityWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityWeatherRepository {

    private val api = Api.getInstance()

    fun fetchCityWeathers(id: Int, callback: ForecastCallback){
        val result = api.getByCityId(id)
        result.enqueue(object : Callback<CityWeather> {
            override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                Log.d("CityWeatherManager", "Error: ${t.message}")
            }

            override fun onResponse(call: Call<CityWeather>, response: Response<CityWeather>) {
                callback.onGetForecast(response.body()!!)
            }

        })
    }
}

interface ForecastCallback {
    fun onGetForecast(cityWeather: CityWeather)
}