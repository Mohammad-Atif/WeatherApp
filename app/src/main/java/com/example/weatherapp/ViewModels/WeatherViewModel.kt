package com.example.weatherapp.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.models.WheatherReport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    val current_weather:MutableLiveData<WheatherReport> = MutableLiveData()


    fun getweatherbycity(city:String="Lucknow"){

        CoroutineScope(Dispatchers.IO).launch {
            val res=weather_rep.getweatherbycityname(city)
            if(res.isSuccessful)
            {
                val currenttemp= res.body()
                withContext(Main){
                    current_weather.postValue(currenttemp)
                }
            }

        }
    }
}