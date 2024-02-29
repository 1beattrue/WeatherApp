package edu.mirea.onebeattrue.weatherapp.data.mapper

import edu.mirea.onebeattrue.weatherapp.data.local.model.CityDbModel
import edu.mirea.onebeattrue.weatherapp.domain.entity.City

fun City.toDbModel(): CityDbModel = CityDbModel(
    id = id,
    name = name,
    country = country
)

fun CityDbModel.toEntity(): City = City(
    id = id,
    name = name,
    country = country
)

fun List<CityDbModel>.toEntities(): List<City> = map { it.toEntity() }