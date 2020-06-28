package com.asadkhan.global.network

import com.asadkhan.global.domain.city.details.CityDetailsData
import com.asadkhan.global.domain.city.nearby.CityResponseData
import com.asadkhan.global.domain.city.search.CitySearchResultsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

const val BASE_URL = "https://api.teleport.org/api/"

interface TeleportService {
  
  // Eg: Mumbai https://api.teleport.org/api/locations/19.0760,72.8777/
  // Eg: San Francisco https://api.teleport.org/api/locations/37.7749,122.4194/
  @GET("locations/{latitude},{longitude}/")
  suspend fun fetchNearestCity(@Path("latitude") latitude: Double, @Path("longitude") longitude: Double): Response<CityResponseData>
  
  // Eg: https://api.teleport.org/api/cities/?search=mumbai
  @GET("cities/")
  suspend fun searchCitiesByText(@Query("search") text: String): Response<CitySearchResultsData>
  
  // Eg: https://api.teleport.org/api/cities/geonameid:1275339/
  @GET
  suspend fun getCityDetails(@Url urlString: String): Response<CityDetailsData>
  
}