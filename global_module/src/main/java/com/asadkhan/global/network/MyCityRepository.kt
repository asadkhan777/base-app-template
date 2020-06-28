package com.asadkhan.global.network

import com.asadkhan.global.domain.city.details.CityDetailsData
import com.asadkhan.global.domain.city.nearby.CityResponseData
import com.asadkhan.global.domain.city.search.CitySearchResultsData

public class MyCityRepository(private val service: TeleportService = teleportService) : BaseRepository() {
  
  suspend fun fetchNearestCityData(latitude: Double, longitude: Double): Result<CityResponseData> {
    return makeSafeNetworkCall { service.fetchNearestCity(latitude, longitude) }
  }
  
  suspend fun searchCitiesByText(searchQuery: String): Result<CitySearchResultsData> {
    return makeSafeNetworkCall { service.searchCitiesByText(searchQuery) }
  }
  
  suspend fun getCityDetails(url: String): Result<CityDetailsData> {
    return makeSafeNetworkCall { service.getCityDetails(url) }
  }
}
