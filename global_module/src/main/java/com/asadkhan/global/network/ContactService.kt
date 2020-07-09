package com.asadkhan.global.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

const val BASE_URL = "https://api.teleport.org/api/"

interface ContactService {
  
  @GET("/")
  suspend fun fetchMyContacts(): Response<Any>
  
  @GET("contacts/")
  suspend fun searchContactsByText(@Query("search") text: String): Response<Any>
  
  @GET
  suspend fun getContactDetails(@Url urlString: String): Response<Any>
  
}