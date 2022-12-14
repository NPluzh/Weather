package com.example.weather.utils

import com.example.weather.model.dto.WeatherDTO
import com.example.weather.view.details.OnResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {// TODO HW 5 try catch

    fun requestFirstVariant(lat: Double,lon: Double,onResponse: OnResponse){
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty("X-Yandex-API-Key","ceae3d76-b634-4bfd-8ef5-25a327758ae9")
        Thread{
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
            onResponse.onResponse(weatherDTO)
        }.start()
    }

    fun requestSecondVariant(lat: Double,lon: Double,block:(weather:WeatherDTO)->Unit){
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

        Thread {
            var myConnection: HttpsURLConnection? = null
            myConnection = uri.openConnection() as HttpsURLConnection
            try {
                myConnection.readTimeout = 5000
                myConnection.addRequestProperty(YANDEX_API_KEY, "ceae3d76-b634-4bfd-8ef5-25a327758ae9") // оставил ключ для преподавателя ;)

                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                block(weatherDTO)
            }catch (e:Exception){
            }finally {
                myConnection.disconnect()
            }
        }.start()
    }
}