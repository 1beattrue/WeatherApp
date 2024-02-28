package edu.mirea.onebeattrue.weatherapp.domain.usecase

import edu.mirea.onebeattrue.weatherapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke() = repository.favouriteCities
}