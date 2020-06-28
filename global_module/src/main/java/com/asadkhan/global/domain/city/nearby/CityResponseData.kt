package com.asadkhan.global.domain.city.nearby


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityResponseData(
    @SerializedName("_embedded")
    val embeddedNearestCityData: EmbeddedNearestCityData,
    @SerializedName("coordinates")
    val coordinates: Coordinates
) : Parcelable