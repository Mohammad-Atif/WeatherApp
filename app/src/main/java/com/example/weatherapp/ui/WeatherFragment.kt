package com.example.weatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.ViewModels.WeatherViewModel
import com.example.weatherapp.ViewModels.WeatherViewModelProvider
import com.example.weatherapp.databinding.FragmentWeatherBinding
import kotlin.math.abs


class WeatherFragment : Fragment(R.layout.fragment_weather) {

    lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    private var PERMISSION_REQUEST = 10
    private var permissions= Manifest.permission.ACCESS_FINE_LOCATION
    private  var x1:Float = 0.0f
    private  var x2:Float = 0.0f
    val MIN_DISTANCE=150

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= (activity as MainActivity).viewModel





        viewModel.current_weather.observe(viewLifecycleOwner, Observer {
            val picurl= viewModel.current_weather.value?.current?.weather_icons?.get(0).toString()
            Glide.with(this).load(picurl).into(binding.whetherlogo)
            val temp=viewModel.current_weather.value?.current?.temperature
            val tempstring="$temp°C"
            binding.currentDegreeText.text= tempstring
            val windspeed=viewModel.current_weather.value?.current?.wind_speed
            val precipation=viewModel.current_weather.value?.current?.precip
            val pressure=viewModel.current_weather.value?.current?.pressure
            val windspeedString="Wind: $windspeed kmph"
            val PrecipString="Precip: $precipation mm"
            val PressureString="Pressure: $pressure mb"
            binding.txtWind.text=windspeedString
            binding.txtPrecip.text=PrecipString
            binding.txtpressure.text=PressureString
            val cityname=viewModel.current_weather.value?.location?.name.toString()
            val countryname=viewModel.current_weather.value?.location?.country.toString()
            val txtcityString="$cityname,$countryname"
            binding.txtCity.text = txtcityString
            binding.txtDescription.text= viewModel.current_weather.value?.current?.weather_descriptions?.get(0)
                .toString()

        })






    }








}
