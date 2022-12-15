package com.example.weather.model

import com.example.weather.MyApp
import com.example.weather.domain.City
import com.example.weather.domain.Weather
import com.example.weather.model.dto.WeatherDTO
import com.example.weather.model.room.WeatherEntity
import com.example.weather.utils.YANDEX_API_KEY
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryRoomImpl:RepositoryWeatherByCity,RepositoryWeatherSave,RepositoryWeatherAvailable {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        callback.onResponse(MyApp.getWeatherDatabase().weatherDao().getWeatherByLocation(city.lat,city.lon).let{
            convertHistoryEntityToWeather(it).last()
        })
    }

    override fun addWeather(weather: Weather) {
        MyApp.getWeatherDatabase().weatherDao().insertRoom(convertWeatherToEntity(weather))
    }

    override fun getWeatherAll(callback: CommonListWeatherCallback) {
        callback.onResponse(convertHistoryEntityToWeather(MyApp.getWeatherDatabase().weatherDao().getWeatherAll()))
    }

    private fun convertHistoryEntityToWeather(entityList: List<WeatherEntity>): List<Weather> {
        /* val answer = mutableListOf<WeatherDTO>()
         entityList.forEach {
             answer.add(WeatherDTO(Fact(it.feelsLike,it.temperature)))
         }*/
        return entityList.map {
            Weather(City(it.name, it.lat, it.lon), it.temperature, it.feelsLike)
        }
    }
    private fun convertWeatherToEntity(weather: Weather): WeatherEntity {
        return WeatherEntity(0, weather.city.name, weather.city.lat,weather.city.lon, weather.temperature, weather.feelsLike)
    }



}
