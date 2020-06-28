package com.asadkhan.global.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import com.asadkhan.global.R
import com.asadkhan.global.base.MutableMemoizedLiveData
import com.asadkhan.global.handler
import com.asadkhan.global.toast
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_LOW_POWER
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import timber.log.Timber

const val REQUEST_CHECK_LOCATION_SETTINGS = 10000

object LocationHelper {
  var locationFetchRetryCounter = 0
  
  val lastLocationLiveData = MutableMemoizedLiveData<Location>()
  val locationUpdatesLiveData = MutableMemoizedLiveData<Location>()
}

fun createLocationRequest(requestedInterval: Long = 500, fastInterval: Long = 500): LocationRequest {
  return LocationRequest.create().apply {
    interval = requestedInterval
    fastestInterval = fastInterval
    priority = PRIORITY_LOW_POWER
  } ?: throw IllegalStateException("Unable to build LocationRequest")
}

fun createLocationSettingsRequest(requestedInterval: Long = 1000, fastInterval: Long = 500): LocationSettingsRequest {
  val locationRequest = createLocationRequest(requestedInterval, fastInterval)
  
  val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest).setAlwaysShow(true)
  return builder.build() ?: throw IllegalStateException("Unable to build LocationSettingsRequest")
}

fun Activity.requestLocationSettings(
    settingsRequest: LocationSettingsRequest = createLocationSettingsRequest(),
    errorCallback: () -> Unit = {},
    successCallback: (Location) -> Unit = { }
) {
  LocationHelper.locationFetchRetryCounter = 0
  
  val client = LocationServices.getSettingsClient(this)
  
  val task = client.checkLocationSettings(settingsRequest)
  
  task.addOnSuccessListener { locationSettingsResponse ->
    // All location settings are satisfied. The client can initialize
    // location requests here.
    logNetworkStateData(locationSettingsResponse)
    triggerLocationRequestUpdates(errorCallback = errorCallback, successCallback = successCallback)
  }
  task.addOnFailureListener { exception ->
    if (exception is ResolvableApiException) {
      // Location settings are not satisfied, but this can be fixed
      // by showing the user a dialog.
      try {
        // Show the dialog by calling startResolutionForResult(),
        // and check the result in onActivityResult().
        exception.startResolutionForResult(this, REQUEST_CHECK_LOCATION_SETTINGS)
      } catch (sendEx: IntentSender.SendIntentException) {
        // Ignore the error.
      }
    }
    errorCallback()
  }
}

private fun logNetworkStateData(locationSettingsResponse: LocationSettingsResponse) {
  locationSettingsResponse.locationSettingsStates.apply {
    Timber.d("isLocationPresent $isLocationPresent")
    Timber.d("isLocationUsable: $isLocationUsable")
    Timber.d("isNetworkLocationPresent: $isNetworkLocationPresent")
    Timber.d("isNetworkLocationUsable: $isNetworkLocationUsable")
  }
}

@SuppressLint("MissingPermission")
fun Activity.triggerLocationRequestUpdates(
    locationRequest: LocationRequest = createLocationRequest(),
    errorCallback: () -> Unit = { toast(R.string.unable_to_fetch_location) },
    successCallback: (Location) -> Unit = {}) {
  val requestLocationUpdates = fusedLocationProviderClient().requestLocationUpdates(
      locationRequest,
      getLocationCallback(locationSuccessCallback = successCallback),
      Looper.getMainLooper()
  )
  requestLocationUpdates.addOnFailureListener { error ->
    errorCallback()
    error.printStackTrace()
  }
}

@SuppressLint("MissingPermission")
fun Activity.triggerLocationRequestUpdates(locationRequest: LocationRequest = createLocationRequest(), locationCallback: LocationCallback) {
  fusedLocationProviderClient().requestLocationUpdates(
      locationRequest,
      locationCallback,
      Looper.getMainLooper()
  )
}

private fun Activity.fusedLocationProviderClient() = LocationServices.getFusedLocationProviderClient(this)

fun Activity.getLocationCallback(
    stopOnceLocationReceived: Boolean = true,
    errorCallback: () -> Unit = { toast(R.string.unable_to_fetch_location) },
    locationSuccessCallback: (Location) -> Unit): LocationCallback {
  return object : LocationCallback() {
    override fun onLocationResult(locationResult: LocationResult?) {
      LocationHelper.locationFetchRetryCounter++
      if (locationResult == null) {
        Timber.e("Location result is null, please check")
        if (LocationHelper.locationFetchRetryCounter < 5) {
          handler.postDelayed({
            triggerLocationRequestUpdates(successCallback = locationSuccessCallback)
          }, 700)
        } else {
          errorCallback()
          LocationHelper.locationFetchRetryCounter = 0
        }
      } else {
        LocationHelper.locationFetchRetryCounter = 0
        // Update UI with location data
        if (stopOnceLocationReceived) {
          val lastLocation = locationResult.lastLocation
          locationSuccessCallback(lastLocation)
          LocationHelper.lastLocationLiveData.postValue(lastLocation)
          fusedLocationProviderClient().removeLocationUpdates(this)
        } else {
          for (location in locationResult.locations) {
            locationSuccessCallback(location)
            LocationHelper.locationUpdatesLiveData.postValue(location)
          }
        }
      }
      
    }
  }
}