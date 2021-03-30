package com.example.weatherapp.api

import com.example.weatherapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*
  Creating the main retrofit class which has the retrofit builder and has the api creeted with the
  help of Wheather api
 */

class RetrofitInstance {

    companion object{

        private val retrofit by lazy{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        }

        val api by lazy{
            retrofit.create(WeatherApi::class.java)
        }


    }


}