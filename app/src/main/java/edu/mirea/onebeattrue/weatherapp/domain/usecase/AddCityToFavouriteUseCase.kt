package edu.mirea.onebeattrue.weatherapp.domain.usecase

import edu.mirea.onebeattrue.weatherapp.domain.entity.City
import edu.mirea.onebeattrue.weatherapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class AddCityToFavouriteUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke(city: City) = repository.addToFavourite(city)
}