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
import com.example.weather.R


class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment() // метод newInstance() возвращает WeatherListFragment()
    }

    var isRussian = true

    private var _binding: FragmentWeatherListBinding?= null
    private val binding: FragmentWeatherListBinding
        get(){
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    lateinit var viewModel: WeatherListViewModel
    override fun onCreateView( // процесс создания фрагмента
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherListBinding.inflate(inflater)//надули фрагмент bindingom
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

        binding.weatherListFragmentFAB.setOnClickListener {
            isRussian = !isRussian
            if(isRussian){
                viewModel.getWeatherListForRussia()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_russia)
            }else{
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(appState: AppState){
        when (appState){
            is AppState.Error -> {/*TODO HW*/ }
            AppState.Loading -> {/*TODO HW*/}
            is AppState.SuccessOne -> {
                val result = appState.weatherData
            }
            is AppState.SuccessMulti ->{
                binding.mainFragmentRecyclerView.adapter =WeatherListAdapter(appState.weatherList)
            }
        }
    }


}
