package com.example.weatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.ViewModels.WeatherViewModel
import com.example.weatherapp.ViewModels.WeatherViewModelProvider
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: WeatherViewModel
    private lateinit var binding: ActivityMainBinding
    private  var x1:Float = 0.0f
    private  var x2:Float = 0.0f
    val MIN_DISTANCE=150
    var indetailFrag=false
    private var PERMISSION_REQUEST = 10
    private var permissions= Manifest.permission.ACCESS_FINE_LOCATION
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rep= WeatherRepository()
        val viewmodelprovider= WeatherViewModelProvider(rep)
        viewModel= ViewModelProviders.of(this,viewmodelprovider).get(WeatherViewModel::class.java)
        val frag=WeatherFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_controller_container,frag).commit()

        /*
       In order to use location we have to ask the user for the location permission acces
       If android version is less than marshmellow we didnt need to ask for it
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (viewModel.checkPermission(this,permissions)) {
                viewModel.getCurrentLocation(this)
            } else {
                requestPermissions(arrayOf(permissions), PERMISSION_REQUEST)
            }
        } else {
            viewModel.getCurrentLocation(this)
        }



        binding.btnSearch.setOnClickListener {
            if(!binding.SearchCity.text.isEmpty())
            {
                viewModel.getweatherbycity(binding.SearchCity.text.toString())
                binding.SearchCity.text.clear()
            }
        }

        binding.cardviewWhether.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action==MotionEvent.ACTION_DOWN)
                x1=motionEvent.x
            if(motionEvent.action==MotionEvent.ACTION_UP)
            {
                x2=motionEvent.x
                val deltax=x2-x1
                if(deltax>MIN_DISTANCE){
                    if(indetailFrag)
                    {
                        Log.d("swipe","left2right")
                        indetailFrag=false
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_controller_container,frag).commit()
                    }
//                    Toast.makeText(this,"left2right Swipe", Toast.LENGTH_SHORT).show()
                }
                else if(abs(deltax) >MIN_DISTANCE){
                    //Toast.makeText(this,"right2left Swipe",Toast.LENGTH_SHORT).show()
                    if(!indetailFrag) {
                        Log.d("swipe","right2left")
                        indetailFrag=true
                        val moreDetailsFrag = MoreDetailsFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_controller_container, moreDetailsFrag).commit()
                    }
                }
            }

            return@setOnTouchListener true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==PERMISSION_REQUEST){
            if(grantResults[0]== PackageManager.PERMISSION_DENIED)
            {
                ActivityCompat.finishAffinity(this)
            }
        }
        viewModel.getCurrentLocation(this)
    }


}
