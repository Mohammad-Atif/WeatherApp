package com.example.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.ViewModels.WeatherViewModel
import com.example.weatherapp.databinding.FragmentMoreDetailsBinding


class MoreDetailsFragment : Fragment() {

    lateinit var binding: FragmentMoreDetailsBinding
    lateinit var viewModel: WeatherViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMoreDetailsBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

        viewModel.current_weather.observe(viewLifecycleOwner, Observer {
            val FeelsLiketemp=viewModel.current_weather.value?.current?.feelslike
            val FeelsLiketempstring="$FeelsLiketemp°C"
            val windDegree=viewModel.current_weather.value?.current?.wind_degree
            val windDegreeString="$windDegree°"
            val humidity=viewModel.current_weather.value?.current?.humidity
            val humidityString="$humidity%"
            val uvindexString=viewModel.current_weather.value?.current?.uv_index.toString()
            val visibility=viewModel.current_weather.value?.current?.visibility
            val visibilityString="$visibility km"
            binding.FeelsLikeTxt.text=FeelsLiketempstring
            binding.HumidTxt.text=humidityString
            binding.WindDegreeTxt.text=windDegreeString
            binding.UvIndexTxt.text=uvindexString
            binding.VisibTxt.text=visibilityString
        })

    }

}
