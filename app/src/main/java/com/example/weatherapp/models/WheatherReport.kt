package com.example.weatherapp.models

data class WheatherReport(
    val current: Current,
    val location: Location,
    val request: Request
)