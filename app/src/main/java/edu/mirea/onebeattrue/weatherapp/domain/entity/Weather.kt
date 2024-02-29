package edu.mirea.onebeattrue.weatherapp.domain.entity

import java.util.Calendar

data class Weather(
    val tempC: Float,
    val conditionText: String,
    val iconUrl: String,
    val date: Calendar
)
