package com.example.weatherapp.ViewModels

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.PermissionChecker.PERMISSION_DENIED
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.models.WheatherReport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


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
    val locationlive:MutableLiveData<Location> = MutableLiveData()
    lateinit var locationManager: LocationManager
    private var hasGps = false
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

    fun checkPermission(context: Context,permiss: String): Boolean {
        var allSuccess = true
        if (checkCallingOrSelfPermission(context,permiss) == PERMISSION_DENIED)
            allSuccess = false
        return allSuccess
    }



    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context){
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (localGpsLocation != null)
        {
            locationlive.postValue(localGpsLocation)
            Log.d("forLocation","${localGpsLocation.latitude} : ${localGpsLocation.longitude}")
            getCityNameFromLocation(context,localGpsLocation.latitude,localGpsLocation.longitude)
        }

    }

    fun getCityNameFromLocation(context: Context,lattitude:Double,longitude:Double){
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(lattitude, longitude, 1)
        val cityName = addresses[0].getAddressLine(0)
        Log.d("cityFromLocation","$cityName")
        var firstcomma=0
        var secondcomma=0
        for(i in cityName.indices)
        {
            if(cityName[i]==',' && firstcomma==0)
                firstcomma=i
            else if(cityName[i]==',' && secondcomma==0)
                secondcomma=i
            if(firstcomma!=0 && secondcomma!=0)
                break

        }
        val currentcityname=cityName.subSequence(firstcomma,secondcomma).toString()
        Log.d("OnlycityFromLocation","$currentcityname")
        getweatherbycity(cityName)
    }
}