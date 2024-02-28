package edu.mirea.onebeattrue.weatherapp.domain.usecase

import edu.mirea.onebeattrue.weatherapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class RemoveCityFromFavouriteUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke(cityId: Int) = repository.removeFromFavourite(cityId)
}