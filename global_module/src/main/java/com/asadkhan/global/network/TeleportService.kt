package com.asadkhan.global.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

const val BASE_URL = "https://api.teleport.org/api/"

interface TeleportService {
  
  @GET("locations/{latitude},{longitude}/")
  suspend fun fetchNearestCity(@Path("latitude") latitude: Double, @Path("longitude") longitude: Double): Response<CityResponseData>
  
  @GET("cities/")
  suspend fun searchCitiesByText(@Query("search") text: String): Response<CityResponseData>
  
  @GET
  suspend fun getCityDetails(@Url urlString: String): Response<CityResponseData>
  
}