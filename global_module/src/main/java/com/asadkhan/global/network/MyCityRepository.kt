package com.asadkhan.global.network

public class MyCityRepository(private val service: TeleportService = teleportService) : BaseRepository() {
  
  suspend fun fetchNearestCity(latitude: Double, longitude: Double): Result<CityResponseData> {
    return makeSafeNetworkCall { service.fetchNearestCity(latitude, longitude) }
  }
  suspend fun searchCitiesByText(searchQuery: String): Result<CityResponseData> {
    return makeSafeNetworkCall { service.searchCitiesByText(searchQuery) }
  }
}
