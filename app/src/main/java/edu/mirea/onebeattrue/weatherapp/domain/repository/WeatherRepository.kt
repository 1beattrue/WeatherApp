package edu.mirea.onebeattrue.weatherapp.domain.repository

import edu.mirea.onebeattrue.weatherapp.domain.entity.Forecast
import edu.mirea.onebeattrue.weatherapp.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast

}