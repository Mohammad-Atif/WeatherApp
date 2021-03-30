package com.example.weatherapp.api

import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

/*
  This is the api interface which contains the function to get the data from the api
  A get reqeust is annotated with @GET annotation and the endpoint after the baseurl and before ? in the url is passes in brackets after get
  and the query parameters are as parameter of the suspend functin with the annotation as @Query

 */
interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getweatherbycity(
        @Query("q")
        cityname: String="London",
        @Query("appid")
        api_key: String = API_KEY
    ): Single<Response<WeatherResponse>>

}