package edu.mirea.onebeattrue.weatherapp.domain.repository

import edu.mirea.onebeattrue.weatherapp.domain.entity.City

interface SearchRepository {

    suspend fun search(query: String): List<City>

}