package com.example.weatherapp.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.models.WeatherResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import rx.Single

/*
 This is the viewmodel which calls the api function and hold the response in the livedata
 from this we can observe from the fragment or activity
 It has one constructer value passes which is instance of WheatherRepository so that it can call the api fucntion \
 from the methods of repository class
 But because we passes a constructer parameter in this class we need to provide our own ViewModelProvider for this

 */
class WeatherViewModel(
    val weather_rep: WeatherRepository
):ViewModel() {

    val current_weather:MutableLiveData<Single<Response<WeatherResponse>>> = MutableLiveData()

    fun getweatherbycity(city:String="Lucknow")= viewModelScope.launch {

        val weather=weather_rep.getweatherbycityname(city)
            //current_weather.postValue(weather)


    }

}