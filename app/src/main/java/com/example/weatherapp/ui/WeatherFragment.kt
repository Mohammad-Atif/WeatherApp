package com.example.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.ViewModels.WeatherViewModel
import com.example.weatherapp.ViewModels.WeatherViewModelProvider
import com.example.weatherapp.databinding.FragmentWeatherBinding
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment(R.layout.fragment_weather) {

    lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rep=WeatherRepository()
        val viewmodelprovider=WeatherViewModelProvider(rep)

        viewModel= ViewModelProviders.of(this,viewmodelprovider).get(WeatherViewModel::class.java)




        viewModel.getweatherbycity("Lucknow")



        viewModel.current_weather.observe(viewLifecycleOwner, Observer {
            val picurl= viewModel.current_weather.value?.current?.weather_icons?.get(0).toString()
            Glide.with(this).load(picurl).into(binding.whetherlogo)
            binding.currentDegreeText.text= viewModel.current_weather.value?.current?.temperature.toString()
            val windspeed=viewModel.current_weather.value?.current?.wind_speed
            val precipation=viewModel.current_weather.value?.current?.precip
            val pressure=viewModel.current_weather.value?.current?.pressure
            binding.txtWind.text="Wind: $windspeed kmph"
            binding.txtPrecip.text="Precip: $precipation mm"
            binding.txtpressure.text="Pressure: $pressure mb"

        })

    }


}
