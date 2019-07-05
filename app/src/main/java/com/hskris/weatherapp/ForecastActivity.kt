package com.hskris.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hskris.weatherapp.data.CityWeatherManager
import com.hskris.weatherapp.data.ForecastCallback
import com.hskris.weatherapp.data.models.City
import com.hskris.weatherapp.data.models.CityWeather
import com.hskris.weatherapp.data.models.Forecast
import kotlinx.android.synthetic.main.activity_forecast.*
import java.util.*
import android.app.Activity
import android.R.attr.data



class ForecastActivity : AppCompatActivity() {

    private val manager = CityWeatherManager()
    var weathers: MutableList<CityWeather> = mutableListOf()
    val adapter = ForecastAdapter(mutableListOf<Forecast>())
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
                adapter.updateForecast(weather.getForecasts())
                return
            }
        }

        manager.fetchCityWeathers(city.id, object: ForecastCallback{
            override fun onGetForecast(forecasts: List<Forecast>) {
                val city = CityWeather(city.id, city.name, "ID")

                val currentWeather = forecasts[0]

                textViewBigTemp.text = currentWeather.temp.toInt().toString()

                val cityText = "${city.name}, ${city.country}"
                textViewCity.text = cityText

                textViewDescription.text = currentWeather.description

                setWeatherIcon(currentWeather.weather)

                city.addForecasts(forecasts)
                weathers.add(city)

                recyclerViewForecast.adapter = ForecastAdapter(forecasts)
            }
        })
    }

    fun setWeatherIcon(weather: String){
        when(weather){
            "Clouds" -> imageViewWeather.setImageResource(R.drawable.cloudy)
            "Rain" -> imageViewWeather.setImageResource(R.drawable.rainy)
            "Clear" -> imageViewWeather.setImageResource(R.drawable.clear)
        }
    }

}

class ForecastAdapter(var items: List<Forecast>) : RecyclerView.Adapter<ForecastAdapter.ForecastItem>(){

    private val calendar = Calendar.getInstance()

    fun updateForecast(newItems: List<Forecast>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastItem {
        return ForecastItem(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.forecast_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ForecastItem, position: Int) {
        val item = items[position]

        val date = item.date
        calendar.setTime(date)
        holder.day.text = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US)?.toString()

        val temp = item.temp.toInt().toString() + " C"
        holder.temp.text = temp

        holder.desc.text = item.weather
    }


    class ForecastItem(itemView: View): RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.textViewDay)
        val temp: TextView = itemView.findViewById(R.id.textViewTemp)
        val desc: TextView = itemView.findViewById(R.id.textViewDescription)
    }
}
