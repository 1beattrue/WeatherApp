package edu.mirea.onebeattrue.weatherapp.data.repository

import edu.mirea.onebeattrue.weatherapp.data.local.db.FavouriteCitiesDao
import edu.mirea.onebeattrue.weatherapp.data.mapper.toDbModel
import edu.mirea.onebeattrue.weatherapp.data.mapper.toEntities
import edu.mirea.onebeattrue.weatherapp.domain.entity.City
import edu.mirea.onebeattrue.weatherapp.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteCitiesDao: FavouriteCitiesDao
) : FavouriteRepository {

    override val favouriteCities: Flow<List<City>>
        get() = favouriteCitiesDao.getFavouriteCities().map { it.toEntities() }

    override fun observeIsFavourite(cityId: Int): Flow<Boolean> = favouriteCitiesDao
        .observeIsFavourite(cityId)

    override suspend fun addToFavourite(city: City) {
        favouriteCitiesDao.addToFavourite(city.toDbModel())
    }

    override suspend fun removeFromFavourite(cityId: Int) {
        favouriteCitiesDao.removeFromFavourite(cityId)
    }

}