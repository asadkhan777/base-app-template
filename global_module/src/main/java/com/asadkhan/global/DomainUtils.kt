package com.asadkhan.global

import com.asadkhan.global.database.city.CityDetails
import com.asadkhan.global.domain.city.details.CityDetailsData

fun CityDetailsData.convertToEntity(favInput: Boolean = isFavorite): CityDetails {
  val details = cityDetailLinks
  return CityDetails(
      fullName = fullName,
      geonameId = geonameId,
      latitude = location.latLong.latitude,
      longitude = location.latLong.longitude,
      name = name,
      population = population,
      province = details.cityAdmin1Division.name,
      country = details.cityCountry.name,
      timezone = details.cityTimezone.name,
      urbanArea = details.cityUrbanArea?.name ?: details.cityAdmin1Division.name,
      selfUrl = details.selfCityLink.href,
      isFavorite = favInput
  )
}