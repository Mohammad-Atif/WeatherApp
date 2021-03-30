package com.example.weatherapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.Repository.WeatherRepository

class WeatherViewModelProvider(
    val weather_rep:WeatherRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(weather_rep) as T
    }

}