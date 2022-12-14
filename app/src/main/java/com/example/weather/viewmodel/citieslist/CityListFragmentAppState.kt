package com.example.weather.viewmodel.citieslist

import com.example.weather.domain.Weather

sealed class CityListFragmentAppState {
    data class SuccessOne(val weatherData: Weather) : CityListFragmentAppState()
    data class SuccessMulti(val weatherList: List<Weather>) : CityListFragmentAppState()
    data class Error(val error: Throwable) : CityListFragmentAppState()
    object Loading : CityListFragmentAppState()
}