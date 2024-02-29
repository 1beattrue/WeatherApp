package edu.mirea.onebeattrue.weatherapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import edu.mirea.onebeattrue.weatherapp.data.local.db.FavouriteCitiesDao
import edu.mirea.onebeattrue.weatherapp.data.local.db.FavouriteDatabase
import edu.mirea.onebeattrue.weatherapp.data.network.api.ApiFactory
import edu.mirea.onebeattrue.weatherapp.data.network.api.ApiService
import edu.mirea.onebeattrue.weatherapp.data.repository.FavouriteRepositoryImpl
import edu.mirea.onebeattrue.weatherapp.data.repository.SearchRepositoryImpl
import edu.mirea.onebeattrue.weatherapp.data.repository.WeatherRepositoryImpl
import edu.mirea.onebeattrue.weatherapp.domain.repository.FavouriteRepository
import edu.mirea.onebeattrue.weatherapp.domain.repository.SearchRepository
import edu.mirea.onebeattrue.weatherapp.domain.repository.WeatherRepository


@Module
interface DataModule {

    @[Binds ApplicationScope]
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @[Binds ApplicationScope]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @[Binds ApplicationScope]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @[Provides ApplicationScope]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[Provides ApplicationScope]
        fun provideFavouriteDatabase(
            context: Context
        ): FavouriteDatabase = FavouriteDatabase.getInstance(context)

        @[Provides ApplicationScope]
        fun provideFavouriteCitiesDao(
            database: FavouriteDatabase
        ): FavouriteCitiesDao = database.favouriteCitiesDao()
    }

}