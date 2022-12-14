package com.example.weather.model

import com.example.weather.domain.Weather
import com.example.weather.domain.getDefaultCity
import com.example.weather.domain.getRussianCities
import com.example.weather.domain.getWorldCities
import com.example.weather.model.dto.Fact
import com.example.weather.model.dto.WeatherDTO

class RepositoryDetailsLocalImpl:RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city.lat==lat&&it.city.lon==lon  }
        callback.onResponse(convertModelToDto(response.first()))
    }
    private  fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
        val fact: Fact = weatherDTO.fact
        return (Weather(getDefaultCity(), fact.temp, fact.feelsLike))
    }

    private fun convertModelToDto(weather:Weather): WeatherDTO{
        val fact: Fact = Fact(weather.feelsLike,weather.temperature)
        return WeatherDTO(fact)
    }


}