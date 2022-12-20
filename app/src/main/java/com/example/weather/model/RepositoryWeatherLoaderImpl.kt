package com.example.weather.model

import com.example.weather.domain.City
import com.example.weather.model.dto.WeatherDTO
import com.example.weather.utils.YANDEX_API_KEY
import com.example.weather.utils.bindDTOWithCity
import com.example.weather.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryWeatherLoaderImpl : RepositoryWeatherByCity {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        Thread {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
            var myConnection: HttpsURLConnection? = null
            myConnection = uri.openConnection() as HttpsURLConnection
            try {
                myConnection.readTimeout = 5000
                myConnection.addRequestProperty(
                    YANDEX_API_KEY,
                    "ceae3d76-b634-4bfd-8ef5-25a327758ae9"
                )

                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                callback.onResponse(bindDTOWithCity(weatherDTO, city))
            } catch (e: IOException) {
                callback.onFailure(e)
            } finally {
                myConnection.disconnect()
            }
        }.start()
    }

}