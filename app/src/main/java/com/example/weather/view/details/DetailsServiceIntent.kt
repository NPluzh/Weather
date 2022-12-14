package com.example.weather.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather.BuildConfig
import com.example.weather.domain.City
import com.example.weather.model.dto.WeatherDTO
import com.example.weather.utils.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsServiceIntent : IntentService("") {

    override fun onHandleIntent(intent: Intent?) {

        intent?.let {
            it.getParcelableExtra<City>(BUNDLE_CITY_KEY)?.let {
                try {
                    val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${it.lat}&lon=${it.lon}")
                    Thread {
                        var myConnection: HttpsURLConnection? = null
                        myConnection = uri.openConnection() as HttpsURLConnection
                        try {
                            myConnection.readTimeout = 5000
                            myConnection.addRequestProperty(
                                YANDEX_API_KEY,
                                "ceae3d76-b634-4bfd-8ef5-25a327758ae9"
                            )

                            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                            val weatherDTO =
                                Gson().fromJson(getLines(reader), WeatherDTO::class.java)

                            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
                                putExtra(BUNDLE_WEATHER_DTO_KEY, weatherDTO)
                                action = WAVE
                            })

                        } catch (e: MalformedURLException) {

                        } catch (e: IOException) {

                        } catch (e: JsonSyntaxException) {

                        } finally {
                            myConnection.disconnect()
                        }
                    }.start()
                }catch (e: MalformedURLException){

                }

            }

        }
    }
}