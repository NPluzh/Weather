package com.example.weather.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.*
import com.example.weather.viewmodel.AppState

class WeatherListViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) :
    ViewModel() {

    lateinit var repositoryMulti: RepositoryMany
    lateinit var repositoryOne: RepositoryOne

    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository(){
        repositoryOne = if(isConnection()){
            RepositoryRemoteImpl()
        }else{
            RepositoryLocalImpl()
        }
        repositoryMulti =RepositoryLocalImpl()
    }


    fun getWeatherListForRussia(){
        sentRequest(Location.Russian)
    }
    fun getWeatherListForWorld(){
        sentRequest(Location.World)
    }

    private fun sentRequest(location: Location) {
        //choiceRepository()
        liveData.value = AppState.Loading
        if(false){ //FIXME
            liveData.postValue(AppState.Error(throw IllegalStateException("что-то пошлло не так")))
        }else{
            liveData.postValue(AppState.SuccessMulti(repositoryMulti.getListWeather(location)))
        }

    }

    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() { // TODO HW ***
        super.onCleared()
    }
}