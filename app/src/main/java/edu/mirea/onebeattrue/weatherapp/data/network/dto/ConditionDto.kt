package edu.mirea.onebeattrue.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ConditionDto(
    @SerializedName("text") val conditionText: String,
    @SerializedName("icon") val iconUrl: String,
)
