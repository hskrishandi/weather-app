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
import com.hskris.weatherapp.types.DayNightType
import com.hskris.weatherapp.utils.CityWeatherUtils
import java.text.SimpleDateFormat
import java.util.*


class ForecastActivity : AppCompatActivity() {

    private val cityWeatherRepo = CityWeatherRepository()
    var cityWeathers: MutableList<CityWeather> = mutableListOf()
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
        for(cw in cityWeathers){
            if(cw.city.id == city.id){
                Log.d("ForecastActivity", "${city.name}: reading from fetched dataset")
                setWeatherDisplay(cw.city, cw.getForecasts())
                return
            }
        }

        cityWeatherRepo.fetchCityWeathers(city.id, object: ForecastCallback {
            override fun onGetForecast(cityWeather: CityWeather) {
                Log.d("ForecastActivity", "${city.name}: fetching from API")

                val forecasts = CityWeatherUtils.getFiveDaysForecast(cityWeather)

                cityWeathers.add(cityWeather)

                setWeatherDisplay(cityWeather.city, forecasts)
            }
        })
    }

    fun setWeatherDisplay(city: City, forecasts: List<Forecast>){

        val currentForecast = forecasts[0]
        val textCity = "${city.name}, ${city.country}"

        val dayNight = getDayNight(city.timezone)

        when(dayNight){
            DayNightType.DAY -> forecastLayout.setBackgroundResource(R.drawable.day)
            DayNightType.NIGHT -> forecastLayout.setBackgroundResource(R.drawable.night)
        }

        textViewBigTemp.text = currentForecast.temp.toInt().toString()
        textViewCity.text = textCity
        textViewDescription.text = currentForecast.description

        when(currentForecast.weather){
            "Clouds" -> imageViewWeather.setImageResource(R.drawable.cloudy)
            "Rain" -> imageViewWeather.setImageResource(R.drawable.rainy)
            "Clear" ->
                when(dayNight){
                    DayNightType.DAY -> imageViewWeather.setImageResource(R.drawable.clear)
                    DayNightType.NIGHT -> imageViewWeather.setImageResource(R.drawable.moon)
                }
        }

        adapter.updateForecast(forecasts)
    }

    fun getDayNight(timezone: Long): DayNightType {
        var unixtime = System.currentTimeMillis() / 1000L
        unixtime += timezone

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = Date(unixtime*1000)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        when(hour in 6..18) {
            true -> return DayNightType.DAY
            false -> return DayNightType.NIGHT
        }
    }

}