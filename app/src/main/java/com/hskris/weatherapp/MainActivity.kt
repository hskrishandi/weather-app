package com.hskris.weatherapp

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
import com.hskris.weatherapp.data.models.CityWeather
import com.hskris.weatherapp.data.models.Forecast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val manager = CityWeatherManager()
    var weathers: MutableList<CityWeather> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewForecast.layoutManager = LinearLayoutManager(this)
        manager.fetchCityWeathers(1642911, object: ForecastCallback{
            override fun onGetForecast(forecasts: List<Forecast>) {
                val city = CityWeather(1642911, "Jakarta", "ID")
                city.addForecasts(forecasts)
                weathers.add(city)

                recyclerViewForecast.adapter = ForecastAdapter(forecasts)
            }
        })
    }
}

class ForecastAdapter(val items: List<Forecast>) : RecyclerView.Adapter<ForecastAdapter.ForecastItem>(){

    private val calendar = Calendar.getInstance()

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

        val temp = (item.temp.toInt() - 273).toString() + " C"
        holder.temp.text = temp

        holder.desc.text = item.description
    }


    class ForecastItem(itemView: View): RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.textViewDay)
        val temp: TextView = itemView.findViewById(R.id.textViewTemp)
        val desc: TextView = itemView.findViewById(R.id.textViewDescription)
    }
}
