package com.example.weather.model

import com.example.weather.domain.City
import com.example.weather.domain.getRussianCities
import com.example.weather.domain.getWorldCities

class RepositoryLocalImpl:RepositoryWeatherByCity {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city.lat==city.lat&&it.city.lon==city.lon  }
        callback.onResponse((response.first()))
    }
}