package com.hskris.weatherapp.ui.forecast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hskris.weatherapp.data.repository.ForecastCallback
import com.hskris.weatherapp.data.models.City
import com.hskris.weatherapp.data.models.CityWeather
import com.hskris.weatherapp.data.models.Forecast
import kotlinx.android.synthetic.main.activity_forecast.*
import android.app.Activity
import android.util.Log
import com.hskris.weatherapp.R
import com.hskris.weatherapp.ui.city.CityActivity
import com.hskris.weatherapp.data.repository.CityWeatherRepository


class ForecastActivity : AppCompatActivity() {

    private val cityWeatherRepo = CityWeatherRepository()
    var weathers: MutableList<CityWeather> = mutableListOf()
    private val adapter = ForecastAdapter(emptyList())
    private val CHOOSE_CITY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        supportActionBar?.hide()

        recyclerViewForecast.layoutManager = LinearLayoutManager(this)
        recyclerViewForecast.adapter = adapter

        textViewChooseCity.setOnClickListener {
            val intent = Intent(this, CityActivity::class.java)
            startActivityForResult(intent, CHOOSE_CITY)
        }

        fetchWeather(CityActivity.cityItems[0])

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CHOOSE_CITY){
            if (resultCode == Activity.RESULT_OK) {
                val city = data!!.getParcelableExtra<City>(CityActivity.CITY_KEY)
                fetchWeather(city)
            }
        }
    }

    private fun fetchWeather(city: City){
        for(weather in weathers){
            if(weather.getId() == city.id){
                Log.d("ForecastActivity", "${city.name}: reading from fetched dataset")
                val forecasts = weather.getForecasts()
                adapter.updateForecast(forecasts)
                setWeatherInfo(city.name, forecasts[0])
                return
            }
        }

        cityWeatherRepo.fetchCityWeathers(city.id, object: ForecastCallback {
            override fun onGetForecast(forecasts: List<Forecast>) {
                Log.d("ForecastActivity", "${city.name}: fetching from API")
                val city = CityWeather(city.id, city.name, "")

                setWeatherInfo(city.name, forecasts[0])

                city.addForecasts(forecasts)
                weathers.add(city)

                adapter.updateForecast(forecasts)
            }
        })
    }

    fun setWeatherInfo(city: String, forecast: Forecast){
        textViewBigTemp.text = forecast.temp.toInt().toString()
        textViewCity.text = city
        textViewDescription.text = forecast.description

        when(forecast.weather){
            "Clouds" -> imageViewWeather.setImageResource(R.drawable.cloudy)
            "Rain" -> imageViewWeather.setImageResource(R.drawable.rainy)
            "Clear" -> imageViewWeather.setImageResource(R.drawable.clear)
        }
    }

}