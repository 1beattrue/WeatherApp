package edu.mirea.onebeattrue.weatherapp.domain.usecase

import edu.mirea.onebeattrue.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityId: Int) = repository.getForecast(cityId)
}