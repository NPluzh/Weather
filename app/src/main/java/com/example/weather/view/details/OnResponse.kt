package com.example.weather.view.details

import com.example.weather.model.dto.WeatherDTO

fun interface OnResponse {
    fun onResponse(weather: WeatherDTO)
}