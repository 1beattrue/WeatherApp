package edu.mirea.onebeattrue.weatherapp.data.repository

import edu.mirea.onebeattrue.weatherapp.data.mapper.toEntities
import edu.mirea.onebeattrue.weatherapp.data.network.api.ApiService
import edu.mirea.onebeattrue.weatherapp.domain.entity.City
import edu.mirea.onebeattrue.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): SearchRepository {

    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }

}