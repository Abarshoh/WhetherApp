package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class ForecastAdapter (private val days: JSONArray): RecyclerView.Adapter<ForecastAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val day: TextView = itemView.findViewById(R.id.forecast_day)
        val temp: TextView = itemView.findViewById(R.id.forecast_temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false))
    }

    override fun getItemCount(): Int {
        return days.length()
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val day = days.getJSONObject(position)
        val date = day.getString("date")
        val temp = day.getJSONObject("day").getDouble("avgtemp_c")

        holder.day.text = date
        holder.temp.text = temp.toString()
    }
}