package com.example.weatherapp.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.models.WheatherReport
import com.example.weatherapp.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
  This is the api interface which contains the function to get the data from the api
  A get reqeust is annotated with @GET annotation and the endpoint after the baseurl and before ? in the url is passes in brackets after get
  and the query parameters are as parameter of the suspend functin with the annotation as @Query

 */
interface WeatherApi {

    @GET("current")
    suspend fun getweatherbycity(
        @Query("query")
        city_name: String,
        @Query("access_key")
        api_key: String = API_KEY


    ): Response<WheatherReport>
}