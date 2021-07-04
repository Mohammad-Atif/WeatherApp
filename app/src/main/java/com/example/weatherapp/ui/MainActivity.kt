package com.example.weatherapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.ViewModels.WeatherViewModel
import com.example.weatherapp.ViewModels.WeatherViewModelProvider
import com.example.weatherapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: WeatherViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rep= WeatherRepository()
        val viewmodelprovider= WeatherViewModelProvider(rep)
        viewModel= ViewModelProviders.of(this,viewmodelprovider).get(WeatherViewModel::class.java)
        val frag=WeatherFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_controller_container,frag).commit()


        binding.btnSearch.setOnClickListener {
            if(!binding.SearchCity.text.isEmpty())
            {
                viewModel.getweatherbycity(binding.SearchCity.text.toString())
                binding.SearchCity.text.clear()
            }
        }
    }


}
