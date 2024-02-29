package edu.mirea.onebeattrue.weatherapp.data.network.api

import edu.mirea.onebeattrue.weatherapp.data.network.dto.CityDto
import edu.mirea.onebeattrue.weatherapp.data.network.dto.CurrentDto
import edu.mirea.onebeattrue.weatherapp.data.network.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json?key=951e3cba57a94288a7f193431232412")
    suspend fun loadCurrent(
        @Query("q") query: String
    ): CurrentDto

    @GET("forecast.json?key=951e3cba57a94288a7f193431232412")
    suspend fun loadForecast(
        @Query("q") query: String,
        @Query("days") daysCount: Int = 4
    ): WeatherForecastDto


    @GET("search.json?key=951e3cba57a94288a7f193431232412")
    suspend fun searchCity(
        @Query("q") query: String
    ): List<CityDto>
}