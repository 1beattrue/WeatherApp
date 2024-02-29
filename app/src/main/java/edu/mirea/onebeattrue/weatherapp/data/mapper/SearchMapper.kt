package edu.mirea.onebeattrue.weatherapp.data.mapper

import edu.mirea.onebeattrue.weatherapp.data.network.dto.CityDto
import edu.mirea.onebeattrue.weatherapp.domain.entity.City


fun CityDto.toEntity(): City = City(
    id = id,
    name = name,
    country = country
)

fun List<CityDto>.toEntities(): List<City> = map { it.toEntity() }