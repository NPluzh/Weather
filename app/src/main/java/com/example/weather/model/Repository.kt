package com.example.weather.model

import com.example.weather.domain.Weather

fun interface RepositoryDetails {
    fun getWeather(lat: Double, lon: Double):Weather
}


fun interface RepositoryOne {
    fun getWeather( lat: Double, lon: Double):Weather
}
fun interface RepositoryCitiesList {
    fun getListCities(location:Location):List<Weather>
}

sealed class Location{
    object Russian:Location()
    object World:Location()
}