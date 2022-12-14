package com.example.weather.viewmodel.wheatherhistorylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.domain.Weather
import com.example.weather.model.CommonListWeatherCallback
import com.example.weather.model.RepositoryRoomImpl
import com.example.weather.model.RepositoryWeatherAvailable
import java.io.IOException

class WeatherHistoryListViewModel(private val liveData: MutableLiveData<WeatherHistoryListFragmentAppState> = MutableLiveData<WeatherHistoryListFragmentAppState>()) :
    ViewModel() {

    lateinit var repository: RepositoryWeatherAvailable
    //lateinit var repositoryOne: RepositoryOne

    fun getLiveData(): MutableLiveData<WeatherHistoryListFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = RepositoryRoomImpl()
    }


    fun getAllHistory() {
        //choiceRepository()
        liveData.value = WeatherHistoryListFragmentAppState.Loading
        repository.getWeatherAll(callback)
    }

    private val callback = object : CommonListWeatherCallback {
        override fun onResponse(listWeather: List<Weather>) {
            liveData.postValue(WeatherHistoryListFragmentAppState.SuccessMulti(listWeather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(WeatherHistoryListFragmentAppState.Error(e))
        }
    }

}