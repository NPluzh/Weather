package com.example.weather.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.databinding.FragmentWeatherListBinding
import com.example.weather.viewmodel.AppState

class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment() // метод newInstance() возвращает WeatherListFragment()
    }

    private var binding: FragmentWeatherListBinding?= null // объявили binding

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    lateinit var viewModel: WeatherListViewModel
    override fun onCreateView( // процесс создания фрагмента
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater)//надули фрагмент bindingom
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {// реагируем, когда фрагмент создан
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)//получение ViewModel из "чана"
        viewModel.getLiveData().observe(viewLifecycleOwner,object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })
        viewModel.sentRequest()
    }

    private fun renderData(appState: AppState){
        when (appState){
            is AppState.Error -> {/*TODO HW*/ }
            AppState.Loading -> {/*TODO HW*/}
            is AppState.Success -> {
                val result = appState.weatherData
                binding!!.cityName.text = result.city.name
                binding!!.temperatureValue.text = result.temperature.toString()
                binding!!.feelsLikeValue.text = result.feelsLike.toString()
                binding!!.cityCoordinates.text = "${result.city.lat}/${result.city.lon}"
            }
        }
    }


}
