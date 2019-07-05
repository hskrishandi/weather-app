package com.hskris.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hskris.weatherapp.data.models.City
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        val items = listOf(
            City(1642911, "Jakarta"),
            City(1277333, "Bangalore")
        )

        recyclerViewChooseCity.layoutManager = LinearLayoutManager(this)
        recyclerViewChooseCity.adapter = CityAdapter(items)

    }
}

class CityAdapter(val items: List<City>) : RecyclerView.Adapter<CityAdapter.CityItem>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItem {
        return CityItem(LayoutInflater.from(parent.context).inflate(R.layout.city_row, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CityItem, position: Int) {
        holder.city.text = items[position].name
    }


    class CityItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city: TextView = itemView.findViewById(R.id.textViewCityRow)
    }

}
