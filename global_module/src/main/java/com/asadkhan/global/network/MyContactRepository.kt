package com.asadkhan.global.network

class MyContactRepository(private val service: ContactService = contactService) : BaseRepository() {
  
  suspend fun fetchNearestContact(): Result<Any> {
    return makeSafeNetworkCall { service.fetchMyContacts() }
  }
  
  suspend fun searchContactsByText(searchQuery: String): Result<Any> {
    return makeSafeNetworkCall { service.searchContactsByText(searchQuery) }
  }
}
