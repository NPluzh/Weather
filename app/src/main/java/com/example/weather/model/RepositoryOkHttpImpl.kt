package com.example.weather.model

import com.example.weather.domain.Weather
import com.example.weather.domain.getDefaultCity
import com.example.weather.model.dto.WeatherDTO
import com.example.weather.utils.YANDEX_API_KEY
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryOkHttpImpl:RepositoryDetails {override fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback) {
    val client = OkHttpClient()
    val builder = Request.Builder()
    builder.addHeader(YANDEX_API_KEY, "ceae3d76-b634-4bfd-8ef5-25a327758ae9")
    builder.url("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
    val request: Request = builder.build()
    val call: Call = client.newCall(request)
    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback.onFailure(e)
        }
        override fun onResponse(call: Call, response: Response) {
            //if (response.isSuccessful) { }
            if (response.code in 200..299 && response.body != null) {
                response.body?.let {
                    val responseString = it.string()
                    val weatherDTO =
                        Gson().fromJson((responseString), WeatherDTO::class.java)
                    callback.onResponse(weatherDTO)
                }
            } else {
                // TODO HW callback.on??? 403 404
                callback.onFailure(IOException("403 404"))
            }
        }
    })
    }
}

