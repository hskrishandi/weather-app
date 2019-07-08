package com.hskris.weatherapp.ui.city

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hskris.weatherapp.R
import com.hskris.weatherapp.data.models.City

class CityAdapter(val items: List<City>) : RecyclerView.Adapter<CityAdapter.CityItem>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItem {
        return CityItem(
            LayoutInflater.from(parent.context).inflate(
                R.layout.city_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CityItem, position: Int) {
        holder.city.text = items[position].name

        holder.itemView.setOnClickListener{
            val intent = Intent()
            intent.putExtra(CityActivity.CITY_KEY, items[position])

            val activity = holder.itemView.context as Activity
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
        }
    }


    class CityItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city: TextView = itemView.findViewById(R.id.textViewCityRow)
    }

}