package com.example.weatherapp.Repository

import com.example.weatherapp.api.RetrofitInstance


/*
 This is the repo class which has the function to get data from the database and api
 Its instance is passed in the viewmodel so that from there we can get the data from the repo
 */

class WeatherRepository{

    suspend fun getweatherbycityname(cityname:String)=RetrofitInstance.api.getweatherbycity(cityname)

}