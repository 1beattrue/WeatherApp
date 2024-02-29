package edu.mirea.onebeattrue.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecastDto(
    @SerializedName("current") val current: CurrentWeatherDto,
    @SerializedName("forecast") val forecast: ForecastDto,
)
