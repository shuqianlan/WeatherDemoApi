package com.ilifesmart.weatherdemoapi.weather

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WeatherHourAdapter private constructor(): RecyclerView.Adapter<WeatherHourAdapter.HourHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class HourHolder(val itemView:View): RecyclerView.ViewHolder(itemView)
}