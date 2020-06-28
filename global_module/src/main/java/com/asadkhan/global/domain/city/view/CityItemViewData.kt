package com.asadkhan.global.domain.city.view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityItemViewData(
    val fullName: String,
    val cityLinkUrl: String
) : Parcelable {
  fun getGeoCodeID() = cityLinkUrl.split(":")[2].replace("/", "").toInt()
  fun getCityName() = fullName.split(",")[0].trim()
  fun getUrbanAreaName() = fullName.split(",")[1].trim()
  fun getCountryName() = fullName.split(",")[2].trim()
}