package edu.mirea.onebeattrue.weatherapp.data.repository

import edu.mirea.onebeattrue.weatherapp.data.mapper.toEntity
import edu.mirea.onebeattrue.weatherapp.data.network.api.ApiService
import edu.mirea.onebeattrue.weatherapp.domain.entity.Forecast
import edu.mirea.onebeattrue.weatherapp.domain.entity.Weather
import edu.mirea.onebeattrue.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {
    override suspend fun getWeather(cityId: Int): Weather {
        return apiService.loadCurrent("$PREFIX_CITY_ID$cityId").toEntity()
    }

    override suspend fun getForecast(cityId: Int): Forecast {
        return apiService.loadForecast("$PREFIX_CITY_ID$cityId").toEntity()
    }

    private companion object {
        private const val PREFIX_CITY_ID = "id:"
    }
}