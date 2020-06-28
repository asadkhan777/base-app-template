package com.asadkhan.global.domain.city.search


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class CitySearchResultsData(
    @SerializedName("_embedded")
  val embeddedCitySearchData: EmbeddedCitySearchData,
    @SerializedName("count")
  val count: Int // 1
) : Parcelable