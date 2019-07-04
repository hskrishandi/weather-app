package com.hskris.weatherapp.data.api

import com.hskris.weatherapp.data.models.CityWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast?APPID=${Api.API_KEY}")
    fun getByCityId(@Query("id") cityId: Int): Call<CityWeather>

}