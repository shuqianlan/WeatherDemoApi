package com.ilifesmart.weatherdemoapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilifesmart.weatherdemoapi.weather.WeatherHourAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = WeatherHourAdapter()
    }
}
