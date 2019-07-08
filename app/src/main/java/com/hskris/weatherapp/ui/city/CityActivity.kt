package com.hskris.weatherapp.ui.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hskris.weatherapp.R
import com.hskris.weatherapp.data.models.City
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        recyclerViewChooseCity.layoutManager = LinearLayoutManager(this)
        recyclerViewChooseCity.adapter =
            CityAdapter(cityItems)

    }

    companion object {
        val CITY_KEY = "CITY_KEY"
        val cityItems = listOf(
            City(1642911, "Jakarta"),
            City(1277333, "Bangalore"),
            City(1819729, "Hong Kong"),
            City(5128581, "New York")
        )
    }
}