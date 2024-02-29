package edu.mirea.onebeattrue.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("forecastday") val forecastList: List<DayDto>
)
