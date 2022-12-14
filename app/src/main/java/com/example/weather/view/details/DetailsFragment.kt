package com.example.weather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailsBinding
import com.example.weather.domain.Weather
import com.example.weather.model.dto.WeatherDTO
import com.example.weather.utils.*
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class DetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    lateinit var weatherLocal: Weather

    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }


    // TODO 5 HW  создать DetailsListViewModel + RepositoryRemoteImpl
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val weather = arguments?.let { arg ->
            arg.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)
        }

        weather?.let { weatherLocal ->
            this.weatherLocal = weatherLocal
            viewModel.getWeather(weatherLocal.city.lat,weatherLocal.city.lon)
            viewModel.getLiveData().observe(viewLifecycleOwner) {
                renderData(it)
            }
        }
    }


    // FIXME диссонанс this - как бы приемник?
    private fun renderData(detailsFragmentAppState: DetailsFragmentAppState) {

        when(detailsFragmentAppState){
            is DetailsFragmentAppState.Error -> {}
            DetailsFragmentAppState.Loading -> {}
            is DetailsFragmentAppState.Success -> {
                with(binding) {
                    val weatherDTO = detailsFragmentAppState.weatherData
                    cityName.text = weatherLocal.city.name
                    temperatureValue.text = weatherDTO.fact.temp.toString()
                    feelsLikeValue.text = weatherDTO.fact.feelsLike.toString()
                    cityCoordinates.text = "${weatherLocal.city.lat}/${weatherLocal.city.lon}"

                    //icon.load("https://c1.staticflickr.com/1/186/31520440226_175445c41a_b.jpg"){
                    icon.load("https://i.pinimg.com/originals/de/1f/6f/de1f6f936d497684c4a023dcde8576cc.jpg\n"){
                        error(R.drawable.ic_earth)
                        placeholder(R.drawable.ic_launcher_background)
                        transformations(CircleCropTransformation())
                    }

                    /*Glide.with(this.root)
                        .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                        .into(icon)

                    Picasso.get().load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                        .into(icon)*/

                }
            }
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "sgrrdfge"
        fun newInstance(weather: Weather): DetailsFragment {
            val fr = DetailsFragment()

            fr.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }
            fr.arguments = Bundle().also {
                it.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
                it.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }

            return fr
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}

