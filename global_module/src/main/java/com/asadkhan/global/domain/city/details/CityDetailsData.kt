package com.asadkhan.global.domain.city.details


import android.os.Parcelable
import com.asadkhan.global.domain.city.nearby.Coordinates
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CityDetailsData(
    @SerializedName("_links")
    val cityDetailLinks: CityDetailLinks,
    @SerializedName("full_name")
    val fullName: String, // San Francisco, California, United States
    @SerializedName("geoname_id")
    val geonameId: Int, // 5391959
    @SerializedName("location")
    val location: Coordinates,
    @SerializedName("name")
    val name: String, // San Francisco
    @SerializedName("population")
    val population: Int // 864816
) : Parcelable