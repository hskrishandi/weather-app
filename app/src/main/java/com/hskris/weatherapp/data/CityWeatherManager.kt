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

    val cityWeathers: MutableList<CityWeather> = mutableListOf()

    fun fetchAllCityWeathers(){
        for(id in cityId){
            val result = api.getByCityId(1642911)
            result.enqueue(object: Callback<CityWeather> {
                override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                    Log.d("CityWeatherManager", "Error: $t")
                }

                override fun onResponse(call: Call<CityWeather>, response: Response<CityWeather>) {
                    cityWeathers.add(response.body()!!)
                }

            })
        }
    }

    fun getFiveDaysForecast(id: Int){
        for(cw in cityWeathers){
            if(cw.getId() == id){
                val forecasts = cw.getForecasts()

                val selectedForecast = mutableListOf<Forecast>()
                for(i in 0..4){
                    val index = i * 8
                    if(index < forecasts.size)
                        selectedForecast.add(forecasts[index])
                }

            }
        }
    }



}