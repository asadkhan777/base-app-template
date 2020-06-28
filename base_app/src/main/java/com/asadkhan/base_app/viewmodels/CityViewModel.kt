package com.asadkhan.global.viewModels.city

import androidx.lifecycle.MutableLiveData
import com.asadkhan.global.R
import com.asadkhan.global.base.BaseViewModel
import com.asadkhan.global.convertToEntity
import com.asadkhan.global.database
import com.asadkhan.global.database.city.CityDetails
import com.asadkhan.global.domain.city.view.CityItemViewData
import com.asadkhan.global.isNotNullAndNotEmpty
import com.asadkhan.global.network.Result.Error
import com.asadkhan.global.network.Result.Success
import com.asadkhan.global.network.cityRepository
import com.asadkhan.global.network.getUrlFromID
import com.asadkhan.global.toast
import kotlinx.coroutines.launch

class CityViewModel() : BaseViewModel() {
  
  private val repository by lazy { cityRepository }
  private val cityDetailsDao by lazy { database.cityDetailsDao() }
  
  val errorLiveData = MutableLiveData<Throwable>()
  val savedCitiesListLiveData = MutableLiveData<List<CityDetails>>()
  val searchResultLiveData = MutableLiveData<List<CityItemViewData>>()
  val cityDetailsLiveData = MutableLiveData<CityDetails>()
  
  
  fun fetchSavedCities() {
    showProgressBar.postValue(true)
    ioScope.launch {
      val favoriteCities = cityDetailsDao.getFavoriteCities()
      emptyStateLiveData.postValue(favoriteCities.isNotNullAndNotEmpty())
      savedCitiesListLiveData.postValue(favoriteCities)
      showProgressBar.postValue(false)
    }
  }
  
  fun fetchNearestCity(latitude: Double, longitude: Double) {
    showProgressBar.postValue(true)
    ioScope.launch {
      when (val nearestCityResult = repository.fetchNearestCityData(latitude, longitude)) {
        is Success -> {
          val nearestCityData = nearestCityResult.data
          val locationNearestCities = nearestCityData.embeddedNearestCityData.locationNearestCities
          if (locationNearestCities.isNotEmpty()) {
            val (href, name) = locationNearestCities[0].nearbyCityLink.locationNearestCityLink
            searchCities(name)
          } else {
            emptyStateLiveData.postValue(true)
          }
        }
        is Error   -> {
          sendFetchResultErrorMessage(nearestCityResult)
        }
      }
      showProgressBar.postValue(false)
    }
  }
  
  fun searchCityByText(text: String) {
    showProgressBar.postValue(true)
    ioScope.launch {
      searchCities(text)
      showProgressBar.postValue(false)
    }
  }
  
  fun fetchCityDetails(cityGeoNameID: Int) {
    showProgressBar.postValue(true)
    ioScope.launch {
      val url = getUrlFromID(cityGeoNameID)
      val cachedCityDetails = cityDetailsDao.getCityDetailsByID(cityGeoNameID)
      
      val cityPresent = cachedCityDetails != null
      val cityAbsent = !cityPresent
      
      // Post cached value if present, nulls will be ignored on UI
      cityDetailsLiveData.postValue(cachedCityDetails)
      emptyStateLiveData.postValue(cityAbsent)
      showProgressBar.postValue(cityAbsent)
      
      when (val cityDetailResults = repository.getCityDetails(url)) {
        is Success -> {
          val detailsData = cityDetailResults.data
          val cityDetails = detailsData.convertToEntity()
          if (cachedCityDetails != null) {
            // Existing City, update DB Row
            val detailsToUpdate = cityDetails.copy(isFavorite = cachedCityDetails.isFavorite)
            cityDetailsDao.updateCity(detailsToUpdate)
            cityDetailsLiveData.postValue(detailsToUpdate)
            Navigator.navigationLiveData.postValue(CityDetailsPageCached(cityDetails))
          } else {
            // New City, add new DB Row
            cityDetailsDao.insertCity(cityDetails)
            cityDetailsLiveData.postValue(cityDetails)
            Navigator.navigationLiveData.postValue(CityDetailsPageCached(cityDetails))
          }
        }
        is Error   -> {
          if (cityPresent) {
            emptyStateLiveData.postValue(true)
            toast("Unable to fetch latest data from server. Please try again in some time")
          } else {
            sendFetchDetailsResultErrorMessage(cityDetailResults)
          }
        }
      }
      showProgressBar.postValue(false)
    }
  }
  
  fun markCityFavouriteValue(cityGeoNameID: Int, inputValue: Boolean = true) {
    showProgressBar.postValue(true)
    ioScope.launch {
      val url = getUrlFromID(cityGeoNameID)
      val cachedCityDetails = cityDetailsDao.getCityDetailsByID(cityGeoNameID)
      
      if (cachedCityDetails != null) {
        // Success! Found the city in local DB
        val detailsToUpdate = cachedCityDetails.copy(isFavorite = inputValue)
        cityDetailsDao.updateCity(detailsToUpdate)
        cityDetailsLiveData.postValue(detailsToUpdate)
        
      } else {
        // Will need to fetch the city from API
        when (val cityDetailResults = repository.getCityDetails(url)) {
          is Success -> {
            val detailsData = cityDetailResults.data
            val cityDetails = detailsData.convertToEntity(favInput = inputValue)
            // New City, add new DB Row
            cityDetailsDao.insertCity(cityDetails)
            cityDetailsLiveData.postValue(cityDetails)
          }
          is Error   -> {
            if (inputValue) {
              toast("Unable to mark city as favourite!")
            } else {
              toast("Unable to remove city from favourites!")
            }
            sendFetchDetailsResultErrorMessage(cityDetailResults)
          }
        }
      }
      showProgressBar.postValue(false)
    }
  }
  
  private suspend fun searchCities(text: String) {
    when (val searchCitiesResult = repository.searchCitiesByText(text)) {
      is Success -> {
        val searchResultsData = searchCitiesResult.data
        val count = searchResultsData.count
        if (count == 0) {
          searchResultLiveData.postValue(arrayListOf())
          emptyStateLiveData.postValue(true)
        } else {
          val citySearchResults = searchResultsData.embeddedCitySearchData.citySearchResults
          val cityViewItemsList = citySearchResults.map {
            CityItemViewData(
                fullName = it.matchingFullName,
                cityLinkUrl = it.cityItemLink.cityItem.href
            )
          }
          emptyStateLiveData.postValue(false)
          searchResultLiveData.postValue(cityViewItemsList)
        }
      }
      is Error   -> {
        sendFetchResultErrorMessage(searchCitiesResult)
      }
    }
  }
  
  private fun sendFetchResultErrorMessage(error: Error) {
    val message = error.errorText
    errorLiveData.postValue(Throwable(message))
    searchResultLiveData.postValue(listOf())
  }
  
  private fun sendFetchDetailsResultErrorMessage(error: Error) {
    val message = error.errorText
    errorLiveData.postValue(Throwable(message))
    cityDetailsLiveData.postValue(null)
  }
}