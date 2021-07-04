package com.example.weatherapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.example.weatherapp.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val frag=WeatherFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_controller_container,frag).commit()

    }


}
