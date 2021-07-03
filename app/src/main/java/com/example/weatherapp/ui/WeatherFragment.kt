package com.example.weatherapp.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
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
import java.security.AccessController.checkPermission


class WeatherFragment : Fragment(R.layout.fragment_weather) {

    lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    private var PERMISSION_REQUEST = 10
    private var permissions= Manifest.permission.ACCESS_FINE_LOCATION
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

        /*
        In order to use location we have to ask the user for the location permission acces
        If android version is less than marshmellow we didnt need to ask for it
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (viewModel.checkPermission(requireContext().applicationContext,permissions)) {
                viewModel.getCurrentLocation(requireContext().applicationContext)
            } else {
                requestPermissions(arrayOf(permissions), PERMISSION_REQUEST)
            }
        } else {
            viewModel.getCurrentLocation(requireContext().applicationContext)
        }








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

        binding.btnSearch.setOnClickListener {
            if(!binding.SearchCity.text.isEmpty())
            {
                viewModel.getweatherbycity(binding.SearchCity.text.toString())
                binding.SearchCity.text.clear()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==PERMISSION_REQUEST){
            if(grantResults[0]==PackageManager.PERMISSION_DENIED)
            {
                finishAffinity(requireActivity().parent)
            }
        }
        viewModel.getCurrentLocation(requireContext().applicationContext)
    }


}
