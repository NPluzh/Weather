package com.example.weather.model

import com.example.weather.domain.Weather

class RepositoryRemoteImpl:RepositoryOne{

override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(300L)
        }.start()
        return Weather()
    }
}