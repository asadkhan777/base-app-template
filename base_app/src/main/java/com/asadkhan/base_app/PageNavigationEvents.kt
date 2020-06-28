package com.asadkhan.base_app

import android.os.Parcelable
import com.asadkhan.global.now
import kotlinx.android.parcel.Parcelize

sealed class PageNavigationEvent : Parcelable

@Parcelize
data class SavedCitiesPage(val timestamp: Long = now()) : PageNavigationEvent()

@Parcelize
data class SearchCitiesPage(val timestamp: Long = now()) : PageNavigationEvent()

@Parcelize
data class CityDetailsPage(val geoNameID: Int) : PageNavigationEvent()

