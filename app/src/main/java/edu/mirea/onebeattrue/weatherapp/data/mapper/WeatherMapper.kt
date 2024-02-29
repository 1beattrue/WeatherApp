package edu.mirea.onebeattrue.weatherapp.data.mapper

import edu.mirea.onebeattrue.weatherapp.data.network.dto.CurrentDto
import edu.mirea.onebeattrue.weatherapp.data.network.dto.CurrentWeatherDto
import edu.mirea.onebeattrue.weatherapp.data.network.dto.WeatherForecastDto
import edu.mirea.onebeattrue.weatherapp.domain.entity.Forecast
import edu.mirea.onebeattrue.weatherapp.domain.entity.Weather
import java.util.Calendar
import java.util.Date

fun CurrentDto.toEntity(): Weather = weather.toEntity()

fun CurrentWeatherDto.toEntity(): Weather = Weather(
    tempC = tempC,
    conditionText = condition.conditionText,
    iconUrl = condition.iconUrl.correctImageUrl(),
    date = date.toCalendar()
)

fun WeatherForecastDto.toEntity(): Forecast = Forecast(
    currentWeather = current.toEntity(),
    upcoming = forecast.forecastList.drop(1).map {
        Weather(
            tempC = it.weather.tempC,
            conditionText = it.weather.condition.conditionText,
            iconUrl = it.weather.condition.iconUrl.correctImageUrl(),
            date = it.date.toCalendar()
        )
    }
)

private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.correctImageUrl() = "https:$this".replace(
    "64x64",
    "128x128"
)