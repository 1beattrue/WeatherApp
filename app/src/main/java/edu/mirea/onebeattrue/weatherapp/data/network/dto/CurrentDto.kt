package edu.mirea.onebeattrue.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CurrentDto(
    @SerializedName("current") val weather: CurrentWeatherDto,
)
