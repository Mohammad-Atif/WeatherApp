package com.example.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.ViewModels.WeatherViewModel
import com.example.weatherapp.ViewModels.WeatherViewModelProvider
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment(R.layout.fragment_weather) {

    lateinit var viewModel: WeatherViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rep=WeatherRepository()
        val viewmodelprovider=WeatherViewModelProvider(rep)

        viewModel= ViewModelProviders.of(this,viewmodelprovider).get(WeatherViewModel::class.java)






        btn_get_wheather.setOnClickListener {
            Toast.makeText(activity,"HELLO",Toast.LENGTH_LONG).show()
            viewModel.getweatherbycity("Lucknow")
        }

        viewModel.current_weather.observe(viewLifecycleOwner, Observer {
            //current_Degree_text.text=viewModel.current_weather.value?.main?.temp.toString()
        })

    }


}
